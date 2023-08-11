package naf.norsys.reservation.service;

import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.UnauthorizedException;
import naf.norsys.reservation.model.User;

public interface UserService extends GenericService<User>{
    User findByEmail(String username) throws ElementNotFoundException;


    User changePassword(Long currentUserId, String currentPassword, String newPassword) throws ElementNotFoundException, UnauthorizedException;
}
