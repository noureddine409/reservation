package naf.norsys.reservation.service;

import naf.norsys.reservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService extends GenericService<Reservation>{

    List<Reservation> findByTimeSlot(Long itemId, LocalDateTime start, LocalDateTime end) ;

}
