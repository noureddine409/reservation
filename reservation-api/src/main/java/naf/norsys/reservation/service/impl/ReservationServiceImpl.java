package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ItemAvailabilityException;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.repository.ReservationRepository;
import naf.norsys.reservation.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ReservationServiceImpl extends GenericServiceImpl<Reservation> implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(GenericRepository<Reservation> genericRepository, ModelMapper modelMapper, ReservationRepository reservationRepository) {
        super(genericRepository, modelMapper);
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation save(Reservation reservation) throws ElementAlreadyExistsException {
        final Long reservationId = reservation.getItem().getId();
        final LocalDateTime startDate = reservation.getStartDate();
        final LocalDateTime endDate = reservation.getEndDate();
        final boolean available = reservationRepository.checkAvailability(reservationId, startDate, endDate);
        if (!available) {
            throw new ItemAvailabilityException(null, new ItemAvailabilityException(),
                    CoreConstant.Exception.NOT_AVAILABLE, new Object[]{reservationId,
                    startDate,
                    endDate});
        }
        return reservationRepository.save(reservation);
    }
}
