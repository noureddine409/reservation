package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.repository.UserRepository;
import naf.norsys.reservation.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static naf.norsys.reservation.common.CoreConstant.Exception.ALREADY_EXISTS;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) return user.get();
        throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {email});
    }

    public UserServiceImpl(GenericRepository<User> genericRepository, ModelMapper modelMapper,
                           UserRepository userRepository) {
        super(genericRepository, modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    public User save(User entity) throws ElementAlreadyExistsException {
        if (userRepository.findByEmail(entity.getEmail()).isPresent())
            throw new ElementAlreadyExistsException(null, new ElementAlreadyExistsException(), ALREADY_EXISTS,
                    new Object[]{entity.getEmail()});
        return super.save(entity);
    }
}
