package naf.norsys.reservation.controller;


import naf.norsys.reservation.dto.PasswordChangeDto;
import naf.norsys.reservation.dto.UserDto;
import naf.norsys.reservation.dto.UserPatchDto;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.UserService;
import naf.norsys.reservation.utils.AuthenticationHelper;
import naf.norsys.reservation.utils.MapHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final MapHelper mapHelper;

    private final UserService userService;

    private final AuthenticationHelper authenticationHelper;

    public UserController(MapHelper mapHelper, UserService userService, AuthenticationHelper authenticationHelper) {
        this.mapHelper = mapHelper;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
    }

    @PatchMapping
    public ResponseEntity<UserDto> patch(@RequestBody UserPatchDto patch) throws ElementNotFoundException {
        final Long currentUserId = authenticationHelper.getCurrentUserId();
        final User updatedUser = userService.patch(currentUserId, patch);
        final UserDto userDto = mapHelper.convertToDto(updatedUser, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<UserDto> updatePassword(@RequestBody PasswordChangeDto dto) throws ElementNotFoundException {
        final Long currentUserId = authenticationHelper.getCurrentUserId();
        final User updatedUser = userService.changePassword(currentUserId, dto.getCurrentPassword(), dto.getNewPassword());
        final UserDto userDto = mapHelper.convertToDto(updatedUser, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) throws ElementNotFoundException {
        final User user = userService.findById(id);
        final UserDto userDto = mapHelper.convertToDto(user, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
