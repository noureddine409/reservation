package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.dto.UserPatchDto;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.UnauthorizedException;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.repository.UserRepository;
import naf.norsys.reservation.service.UserService;
import naf.norsys.reservation.utils.MapHelper;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static naf.norsys.reservation.common.CoreConstant.Exception.ALREADY_EXISTS;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MapHelper mapHelper;

    public UserServiceImpl(GenericRepository<User> genericRepository, ModelMapper modelMapper,
                           UserRepository userRepository, PasswordEncoder passwordEncoder, MapHelper mapHelper) {
        super(genericRepository, modelMapper);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapHelper = mapHelper;
    }

    @Override
    public User findByEmail(String email) throws ElementNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) return user.get();
        throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{email});
    }

    @Override
    public User patch(Long id, UserPatchDto patch) throws ElementNotFoundException {
        User user = this.findById(id);
        mapHelper.mapWithSkipNull(patch, user);
        return userRepository.save(user);
    }

    @Override
    public User changePassword(Long userId, String currentPassword, String newPassword) throws ElementNotFoundException, UnauthorizedException {
        User user = this.findById(userId);
        if(!checkPassword(user, currentPassword)){
            throw new UnauthorizedException(new UnauthorizedException(), CoreConstant.Exception.INVALID_PASSWORD, null);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }


    private boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
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
