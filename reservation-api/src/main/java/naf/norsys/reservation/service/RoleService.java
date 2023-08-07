package naf.norsys.reservation.service;

import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Role;

public interface RoleService extends GenericService<Role> {

    Role save(GenericEnum.RoleName role);

    Role findByName(GenericEnum.RoleName name) ;

}
