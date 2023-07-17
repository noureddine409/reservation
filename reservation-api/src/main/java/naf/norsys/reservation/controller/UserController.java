package naf.norsys.reservation.controller;


import io.swagger.annotations.Api;
import naf.norsys.reservation.dto.UserDto;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.GenericService;
import naf.norsys.reservation.utils.ClassTypeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Api(tags = "User Management", description = "Web services for user Management")
public class UserController extends GenericController<User, UserDto> {
    public UserController(ModelMapper modelMapper, ClassTypeProvider classTypeProvider, GenericService<User> genericService) {
        super(modelMapper, classTypeProvider, genericService);
    }
}
