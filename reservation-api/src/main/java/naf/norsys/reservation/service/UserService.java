package naf.norsys.reservation.service;

import naf.norsys.reservation.dto.UserPatchDto;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.UnauthorizedException;
import naf.norsys.reservation.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService extends GenericService<User>{
    User findByEmail(String username) throws ElementNotFoundException;

    User patch(Long id, UserPatchDto patch) throws ElementNotFoundException;

    User changePassword(Long currentUserId, String currentPassword, String newPassword) throws ElementNotFoundException, UnauthorizedException;
}
