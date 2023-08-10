package naf.norsys.reservation.controller;

import jakarta.validation.Valid;
import naf.norsys.reservation.dto.ItemReservationSearchDto;
import naf.norsys.reservation.dto.ReservationDto;
import naf.norsys.reservation.dto.ReservationRequestDto;
import naf.norsys.reservation.dto.UserReservationSearchDto;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.ResourceOwnershipException;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.ItemService;
import naf.norsys.reservation.service.ReservationService;
import naf.norsys.reservation.utils.AuthenticationHelper;
import naf.norsys.reservation.utils.MapHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static naf.norsys.reservation.common.CoreConstant.Exception.AUTHORIZATION_RESOURCE_OWNERSHIP;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ItemService itemService;
    private final ReservationService reservationService;
    private final MapHelper mapHelper;
    private final AuthenticationHelper authenticationHelper;


    public ReservationController(ItemService itemService, ReservationService reservationService, MapHelper mapHelper, AuthenticationHelper authenticationHelper) {
        this.itemService = itemService;
        this.reservationService = reservationService;
        this.mapHelper = mapHelper;
        this.authenticationHelper = authenticationHelper;
    }


    private boolean isNotOwner(Reservation reservation) {
        final Long currentUserId = authenticationHelper.getCurrentUserId();
        final Long resourceOwnerId = reservation.getUser().getId();
        return !currentUserId.equals(resourceOwnerId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getById(@PathVariable("id") Long id) throws ElementNotFoundException {
        final Reservation reservation = reservationService.findById(id);
        final ReservationDto reservationDto = mapHelper.convertToDto(reservation, ReservationDto.class);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) throws ElementNotFoundException {
        final Reservation reservation = reservationService.findById(id);
        if (isNotOwner(reservation)) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }
        return new ResponseEntity<>(reservationService.delete(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> save(@RequestBody @Valid ReservationRequestDto dto) throws ElementAlreadyExistsException {
        final Item item = itemService.checkItemStatus(dto.getItemId());
        final User user = authenticationHelper.getCurrentUser();
        Reservation reservationEntity = Reservation.builder()
                .item(item)
                .user(user)
                .build();
        mapHelper.map(dto, reservationEntity);
        final ReservationDto reservationDto = mapHelper.convertToDto(reservationService.save(reservationEntity), ReservationDto.class);
        return new ResponseEntity<>(reservationDto, HttpStatus.CREATED);
    }

    @PostMapping("/items")
    public ResponseEntity<List<ReservationDto>> searchByItem(@RequestBody @Valid ItemReservationSearchDto dto) {
        final List<Reservation> reservations = reservationService.findByItemAndTimeSlot(dto.getItemId(),
                dto.getPeriod().getStartDate(), dto.getPeriod().getEndDate());
        final List<ReservationDto> reservationDtoList = mapHelper.convertListToDto(reservations, ReservationDto.class);
        return new ResponseEntity<>(reservationDtoList, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<List<ReservationDto>> searchByUser(@RequestBody @Valid UserReservationSearchDto dto) {
        final List<Reservation> reservations = reservationService.findByUserAndTimeSlot(authenticationHelper.getCurrentUserId(),
                dto.getPeriod().getStartDate(), dto.getPeriod().getEndDate());
        final List<ReservationDto> reservationDtoList = mapHelper.convertListToDto(reservations, ReservationDto.class);
        return new ResponseEntity<>(reservationDtoList, HttpStatus.OK);
    }
}
