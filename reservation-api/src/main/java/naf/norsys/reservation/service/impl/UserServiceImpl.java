package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.model.User;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    public UserServiceImpl(GenericRepository<User> genericRepository, ModelMapper modelMapper) {
        super(genericRepository, modelMapper);
    }
}
