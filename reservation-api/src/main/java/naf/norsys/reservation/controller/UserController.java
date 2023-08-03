package naf.norsys.reservation.controller;


import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import naf.norsys.reservation.dto.UserDto;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.GenericService;
import naf.norsys.reservation.service.UserService;
import naf.norsys.reservation.utils.ClassTypeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
@Api(tags = "User Management", description = "Web services for user Management")
public class UserController extends GenericController<User, UserDto> {
    private final UserService userService;

    public UserController(ModelMapper modelMapper, ClassTypeProvider classTypeProvider, GenericService<User> genericService, UserService userService) {
        super(modelMapper, classTypeProvider, genericService);
        this.userService = userService;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto dto) throws ElementAlreadyExistsException {
        User entity = convertToEntity(dto);
        User savedEntity = userService.save(entity);
        return new ResponseEntity<>(convertToDto(savedEntity), HttpStatus.CREATED);
    }
}
