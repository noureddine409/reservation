package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ItemAvailabilityException;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.repository.ReservationRepository;
import naf.norsys.reservation.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReservationServiceImpl extends GenericServiceImpl<Reservation> implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(GenericRepository<Reservation> genericRepository, ModelMapper modelMapper, ReservationRepository reservationRepository) {
        super(genericRepository, modelMapper);
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation save(Reservation reservation) throws ElementAlreadyExistsException {
        final Long itemId = reservation.getItem().getId();
        final LocalDateTime startDate = reservation.getPeriod().getStartDate();
        final LocalDateTime endDate = reservation.getPeriod().getEndDate();
        boolean available = reservationRepository.checkAvailability(itemId, startDate, endDate);
        if (!available) {
            throw new ItemAvailabilityException(null, new ItemAvailabilityException(),
                    CoreConstant.Exception.NOT_AVAILABLE, new Object[]{itemId,
                    startDate,
                    endDate});
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findByItemAndTimeSlot(Long itemId, LocalDateTime start, LocalDateTime end) {
        Specification<Reservation> reservationSpecification = reservationRepository.existingReservationsByItem(itemId, start, end);
        return reservationRepository.findAll(reservationSpecification);
    }

    @Override
    public List<Reservation> findByUserAndTimeSlot(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<Reservation> reservationSpecification = reservationRepository.existingReservationsByUser(userId, startDate, endDate);
        return reservationRepository.findAll(reservationSpecification);    }
}
