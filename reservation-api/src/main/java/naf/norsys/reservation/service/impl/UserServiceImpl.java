package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Role;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.repository.UserRepository;
import naf.norsys.reservation.service.RoleService;
import naf.norsys.reservation.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static naf.norsys.reservation.common.CoreConstant.Exception.ALREADY_EXISTS;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    @Value("${dev.account.first-name}")
    private String devFirstName;
    @Value("${dev.account.last-name}")
    private String devLastName;
    @Value("${dev.account.email}")
    private String devEmail;
    @Value("${dev.account.password}")
    private String devPassword;

    @Value("{admin.account.first-name}")
    private String adminFirstName;
    @Value("{admin.account.last-name}")
    private String adminLastName;
    @Value("{admin.account.email}")
    private String adminEmail;
    @Value("{admin.account.password}")
    private String adminPassword;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) return user.get();
        throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{email});
    }

    @Override
    public void saveDev() {
        try {
            User user = User.builder()
                    .firstName(devFirstName)
                    .lastName(devLastName)
                    .email(devEmail)
                    .password(devPassword)
                    .build();
            final Role role = roleService.findByName(GenericEnum.RoleName.DEV);
            user.addRole(role);
            this.save(user);
        } catch (RuntimeException exception) {
            LOG.info("developer accounts already created");
        }
    }

    @Override
    public void saveAdmin() {
        try {
            User user = User.builder()
                    .firstName(adminFirstName)
                    .lastName(adminLastName)
                    .email(adminEmail)
                    .password(adminPassword)
                    .build();
            final Role role = roleService.findByName(GenericEnum.RoleName.ADMIN);
            user.addRole(role);
            this.save(user);
        } catch (RuntimeException exception) {
            LOG.info("admin accounts already created");
        }
    }

    public UserServiceImpl(GenericRepository<User> genericRepository, ModelMapper modelMapper,
                           UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        super(genericRepository, modelMapper);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User save(User user) throws ElementAlreadyExistsException {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new ElementAlreadyExistsException(null, new ElementAlreadyExistsException(), ALREADY_EXISTS,
                    new Object[]{user.getEmail()});

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
    }
}
