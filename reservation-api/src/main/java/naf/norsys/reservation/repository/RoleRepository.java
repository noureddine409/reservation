package naf.norsys.reservation.repository;

import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Role;

import java.util.Optional;

public interface RoleRepository extends GenericRepository<Role> {

    Optional<Role> findByName(GenericEnum.RoleName name);
}
