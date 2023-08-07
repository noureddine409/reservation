package naf.norsys.reservation.service;

import naf.norsys.reservation.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService extends GenericService<User>{
    User findByEmail(String username);

    void saveDev();

    void saveAdmin();
    }
