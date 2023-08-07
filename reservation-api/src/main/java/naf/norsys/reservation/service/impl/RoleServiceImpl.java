package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Role;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.repository.RoleRepository;
import naf.norsys.reservation.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {

    private final RoleRepository roleRepository;


    public RoleServiceImpl(GenericRepository<Role> genericRepository, ModelMapper modelMapper,
                           RoleRepository roleRepository) {
        super(genericRepository, modelMapper);
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(GenericEnum.RoleName role) {
        Optional<Role> userRole = roleRepository.findByName(role);
        return userRole.orElseGet(() -> roleRepository.save(Role.builder().name(role).build()));

    }

    @Override
    public Role findByName(GenericEnum.RoleName name) {
        Optional<Role> role = roleRepository.findByName(name);
        if(role.isPresent()) return role.get();
        throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {name});

    }
}
