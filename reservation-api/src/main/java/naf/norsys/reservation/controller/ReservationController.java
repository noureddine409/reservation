package naf.norsys.reservation.controller;

import io.swagger.annotations.Api;
import naf.norsys.reservation.dto.ReservationDto;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.service.GenericService;
import naf.norsys.reservation.utils.ClassTypeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
@Api(tags = "Reservation Management", description = "Web services for Reservation Management")
public class ReservationController extends GenericController<Reservation, ReservationDto>{


    public ReservationController(ModelMapper modelMapper, ClassTypeProvider classTypeProvider, GenericService<Reservation> genericService) {
        super(modelMapper, classTypeProvider, genericService);
    }
}
