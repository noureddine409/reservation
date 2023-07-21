package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class ReservationServiceImpl extends GenericServiceImpl<Reservation> implements ReservationService {

    public ReservationServiceImpl(GenericRepository<Reservation> genericRepository, ModelMapper modelMapper) {
        super(genericRepository, modelMapper);
    }
}
