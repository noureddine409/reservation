package naf.norsys.reservation;

import lombok.extern.slf4j.Slf4j;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.service.RoleService;
import naf.norsys.reservation.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ReservationApiApplication implements CommandLineRunner {
    private final UserService userService;

    private final RoleService roleService;

    public ReservationApiApplication(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {

        roleService.save(GenericEnum.RoleName.DEV);
        roleService.save(GenericEnum.RoleName.ADMIN);


        userService.saveDev();
        userService.saveAdmin();


    }

    public static void main(String[] args) {
        SpringApplication.run(ReservationApiApplication.class, args);
    }

}
