package naf.norsys.reservation.service;

import naf.norsys.reservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService extends GenericService<Reservation>{

    List<Reservation> findByItemAndTimeSlot(Long itemId, LocalDateTime start, LocalDateTime end) ;

    List<Reservation> findByUserAndTimeSlot(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
