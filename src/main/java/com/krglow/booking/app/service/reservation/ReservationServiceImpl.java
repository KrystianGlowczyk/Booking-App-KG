package com.krglow.booking.app.service.reservation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.krglow.booking.app.entity.reservation.ReservationEntity;
import com.krglow.booking.app.entity.screening.ScreeningEntity;
import com.krglow.booking.app.entity.seat.SeatEntity;
import com.krglow.booking.app.entity.voucher.VoucherEntity;
import com.krglow.booking.app.repository.reservation.ReservationRepository;
import com.krglow.booking.app.repository.screening.ScreeningRepository;
import com.krglow.booking.app.repository.seat.SeatRepository;
import com.krglow.booking.app.repository.voucher.VoucherRepository;
import com.krglow.booking.app.service.TicketType;
import com.krglow.booking.app.service.reservation.dto.ReservationRequest;
import com.krglow.booking.app.service.reservation.dto.ReservationResponse;
import com.krglow.booking.app.service.reservation.dto.Ticket;
import com.krglow.booking.app.service.reservation.exception.InvalidReservationException;
import com.krglow.booking.app.service.reservation.exception.InvalidVoucherCodeException;
import com.krglow.booking.app.service.seat.SeatService;
import com.krglow.booking.app.service.seat.dto.SeatIdentifier;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;


/**
 * Reservation Service Implementation. It handles the core logic for reservation operations.
 */
@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final int RESERVATION_EXPIRATION_MINS = 15;

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final SeatService seatService;
    private final Validator validator;
    private final VoucherRepository voucherRepository;

    @Value("${app.baseUrl}")
    private String baseUrl;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ScreeningRepository screeningRepository, SeatService seatService, SeatRepository seatRepository, Validator validator,
            VoucherRepository voucherRepository) {
        this.reservationRepository = reservationRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
        this.seatService = seatService;
        this.validator = validator;
        this.voucherRepository = voucherRepository;
    }

    /**
     * Creates a reservation based on a given ReservationRequest.
     *
     * @param reservation a ReservationRequest object containing details about the reservation
     * @return ReservationResponse object containing details about the reservation
     * @throws InvalidReservationException if reservation request is invalid
     * @throws EntityNotFoundException if entities related to the reservation could not be found
     */
    @Override
    @Transactional
    public ReservationResponse createReservation(ReservationRequest reservation) {

        ReservationEntity entity = createReservationFromRequest(reservation);

        entity = saveReservation(entity);

        // send mail
        // emailService sendConfirmationEmail(entity.getEmail(), entity.getConfirmationToken());

        return createReservationResponse(entity);
    }

    /**
     * Saves a reservation in the database.
     *
     * @param entity a ReservationEntity object representing the reservation
     * @return the saved ReservationEntity
     * @throws InvalidReservationException if validation fails
     */
    private ReservationEntity saveReservation(ReservationEntity entity) {

        Set<ConstraintViolation<ReservationEntity>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            violations.forEach(violation -> log.warn("Validation error: {} - {}", violation.getPropertyPath(), violation.getMessage()));
            throw new InvalidReservationException("Błąd walidacji.");
        }

        entity = reservationRepository.save(entity);
        List<SeatEntity> seats = entity.getSeats();
        for (SeatEntity seat : seats) {
            seat.setReservation(entity);
        }
        seatRepository.saveAll(seats);

        return entity;
    }

    /**
     * Creates a ReservationEntity based on a ReservationRequest.
     *
     * @param reservation a ReservationRequest object
     * @return a ReservationEntity object
     * @throws InvalidReservationException if the reservation request is invalid
     * @throws EntityNotFoundException if the screening is not found
     */
    private ReservationEntity createReservationFromRequest(ReservationRequest reservation) {

        checkValidReservationRequestName(reservation);

        ScreeningEntity screening = screeningRepository.findById(reservation.getScreeningId()).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono ekranizacji"));

        LocalDateTime now = LocalDateTime.now();
        if (!now.plusMinutes(RESERVATION_EXPIRATION_MINS).isBefore(screening.getStartTime())) {
            throw new InvalidReservationException("Miejsca można zarezerwować co najmniej 15 minut przed rozpoczęciem seansu.");
        }

        ReservationEntity entity = new ReservationEntity();
        entity.setConfirmed(false);
        entity.setDeleted(false);
        entity.setScreening(screening);
        entity.setName(reservation.getName());
        entity.setSurname(reservation.getSurname());

        LocalDateTime fifteenMinutesAfterReservation = now.plusMinutes(RESERVATION_EXPIRATION_MINS);
        LocalDateTime fifteenMinutesBeforeScreening = screening.getStartTime().minusMinutes(RESERVATION_EXPIRATION_MINS);
        entity.setExpirationTime(fifteenMinutesAfterReservation.isBefore(fifteenMinutesBeforeScreening) ? fifteenMinutesAfterReservation : fifteenMinutesBeforeScreening);

        List<SeatEntity> seats = new ArrayList<>();

        String confirmationToken = UUID.randomUUID().toString();
        entity.setConfirmationToken(confirmationToken);
        entity.setEmail(reservation.getEmail());

        BigDecimal totalAmount = saveSeatsAndGetTotalAmount(reservation, screening, entity, seats);
        String voucher = reservation.getVoucher();
        if (voucher != null && !voucher.trim().isEmpty()) {
            totalAmount = calculateTotalPrice(totalAmount, voucher);
        }

        entity.setSeats(seats);
        entity.setTotalAmount(totalAmount);

        return entity;
    }

    private ReservationResponse createReservationResponse(ReservationEntity entity) {
        ReservationResponse response = new ReservationResponse();
        response.setExpirationTime(entity.getExpirationTime());
        response.setReservationId(entity.getId());
        response.setTotalAmount(entity.getTotalAmount());
        response.setConfirmationLink(baseUrl + "/reservations/confirm?token=" + entity.getConfirmationToken());
        return response;
    }

    private BigDecimal saveSeatsAndGetTotalAmount(ReservationRequest reservation, ScreeningEntity screening, ReservationEntity reservationEntity, List<SeatEntity> seats) {

        BigDecimal totalAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        List<Ticket> tickets = reservation.getSeats();

        if (tickets.isEmpty())
            throw new InvalidReservationException("Rezerwacja powinna dotyczyć co najmniej jednego miejsca");

        tickets.sort(Comparator.comparing(Ticket::getSeat));

        seatService.checkNoSingleSeatLeft(reservationEntity, tickets);

        for (Ticket ticket : tickets) {
            SeatIdentifier seatIdentifier = seatService.splitSeatIdentifier(ticket.getSeat());
            SeatEntity seat = seatRepository.findByScreeningIdAndRowNumberAndNumberAndDeletedFalse(screening.getId(), seatIdentifier.getRowNumber(), seatIdentifier.getSeatNumber())
                    .orElseThrow(() -> new InvalidReservationException("Nie znaleziono miejsca."));

            seat.setReserved(true);
            var type = TicketType.findByName(ticket.getTicketType());

            if (type.isEmpty())
                throw new InvalidReservationException("Niepoprawny typ biletu");

            totalAmount = totalAmount.add(getPrice(type.get()));

            seats.add(seat);
        }

        return totalAmount;
    }

    private boolean isValidName(String name) {
        return name != null && name.matches("[A-Z][a-zA-Z]{2,}");
    }

    private boolean isValidSurname(String surname) {
        return surname != null && surname.matches("[A-Z][a-zA-Z]+(-[A-Z][a-zA-Z]+)?");
    }

    private void checkValidReservationRequestName(ReservationRequest reservationRequest) {
        String name = reservationRequest.getName();
        String surname = reservationRequest.getSurname();

        if (!isValidName(name))
            throw new InvalidReservationException("Niepoprawny format imienia");

        if (!isValidSurname(surname))
            throw new InvalidReservationException("Niepoprawny format nazwiska");
    }

    /**
     * Confirms a reservation.
     *
     * @param reservationId ID of the reservation
     * @return true if the reservation has been confirmed, false otherwise
     * @throws EntityNotFoundException if the reservation is not found
     */
    @Override
    public boolean confirmReservation(Long reservationId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono rezerwacji"));

        reservation.setConfirmed(true);
        reservationRepository.save(reservation);

        return true;
    }

    /**
     * Cancels reservations and returns the number of cancelled reservations.
     *
     * @return The number of cancelled reservations.
     */
    @Override
    @Transactional
    public int cancelReservations() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<ReservationEntity> expiredReservations = reservationRepository.findAllByExpirationTimeBeforeAndConfirmedFalseAndDeletedFalse(now.plusMinutes(1));

        for (ReservationEntity reservation : expiredReservations) {
            List<SeatEntity> seats = seatRepository.findByReservationAndDeletedFalse(reservation);
            for (SeatEntity seat : seats) {
                seat.setReservation(null);
                seat.setReserved(false);
            }
            reservation.setSeats(null);
            reservation.setDeleted(true);

            seatRepository.saveAll(seats);
            reservationRepository.save(reservation);
        }

        return expiredReservations.size();
    }

    /**
     * Confirms a reservation.
     *
     * @param token confirmation token of the reservation
     * @return true if the reservation has been confirmed, false otherwise
     * @throws EntityNotFoundException if the reservation is not found
     */
    @Override
    public boolean confirmReservation(String token) {
        ReservationEntity reservationEntity = reservationRepository.findByConfirmationToken(token).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono rezerwacji"));

        if (reservationEntity.isDeleted())
            return false;

        reservationEntity.setConfirmed(true);
        reservationRepository.save(reservationEntity);

        log.info("The reservation has been confirmed successfully. Reservation ID: {}", reservationEntity.getId());

        return true;
    }

    public BigDecimal getPrice(TicketType ticketType) {
        if (isWeekend()) {
            return ticketType.getWeekendPrice();
        } else {
            return ticketType.getPrice();
        }
    }

    private boolean isWeekend() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek day = now.getDayOfWeek();
        int hour = now.getHour();

        return (day.equals(DayOfWeek.FRIDAY) && hour >= 14) || day.equals(DayOfWeek.SATURDAY) || (day.equals(DayOfWeek.SUNDAY) && hour < 23);
    }

    /**
     * Calculates the total price of a reservation after applying a discount from a voucher.
     *
     * @param totalAmount the total amount before the discount
     * @param voucherCode the code of the voucher to be applied
     * @return the total price after the discount
     * @throws InvalidVoucherCodeException if the voucher code is invalid
     */
    public BigDecimal calculateTotalPrice(BigDecimal totalAmount, String voucherCode) {

        VoucherEntity voucherEntity = voucherRepository.findById(voucherCode).orElseThrow(() -> new InvalidVoucherCodeException("Niepoprawny kod vouchera"));
        BigDecimal discount = voucherEntity.getDiscount();
        BigDecimal discountedPrice = totalAmount.multiply(BigDecimal.valueOf(1.0 - discount.doubleValue() / 100));

        return discountedPrice.setScale(2, RoundingMode.HALF_UP);
    }

}
