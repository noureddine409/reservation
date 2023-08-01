package naf.norsys.reservation.repository;

import naf.norsys.reservation.model.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User> {

    Optional<User> findByEmail(String email);
}
