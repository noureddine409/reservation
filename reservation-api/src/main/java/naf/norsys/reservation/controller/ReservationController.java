package naf.norsys.reservation.controller;

import io.swagger.annotations.Api;
import naf.norsys.reservation.dto.ReservationDto;
import naf.norsys.reservation.dto.ReservationSearchDto;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.GenericService;
import naf.norsys.reservation.service.ItemService;
import naf.norsys.reservation.service.ReservationService;
import naf.norsys.reservation.service.UserService;
import naf.norsys.reservation.utils.ClassTypeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@Api(tags = "Reservation Management", description = "Web services for Reservation Management")
public class ReservationController extends GenericController<Reservation, ReservationDto> {

    private final ItemService itemService;

    private final UserService userService;

    private final ReservationService reservationService;

    public ReservationController(ModelMapper modelMapper, ClassTypeProvider classTypeProvider, GenericService<Reservation> genericService, ItemService itemService, UserService userService, ReservationService reservationService) {
        super(modelMapper, classTypeProvider, genericService);
        this.itemService = itemService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ReservationDto> save(@RequestBody ReservationDto dto) throws ElementAlreadyExistsException {
        final Item item = itemService.checkItemStatus(dto.getItem().getId());
        final User user = userService.findById(dto.getUser().getId());
        Reservation reservationEntity = convertToEntity(dto);
        reservationEntity.setUser(user);
        reservationEntity.setItem(item);
        return new ResponseEntity<>(convertToDto(reservationService.save(reservationEntity)), HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ReservationDto>> search(@RequestBody @Valid ReservationSearchDto dto) {
        final List<Reservation> reservations = reservationService.findByTimeSlot(dto.getItemId(),
                dto.getPeriod().getStart(), dto.getPeriod().getEnd());
        final List<ReservationDto> reservationDtoList = convertListToDto(reservations);

        return new ResponseEntity<>(reservationDtoList, HttpStatus.OK);
    }
}
