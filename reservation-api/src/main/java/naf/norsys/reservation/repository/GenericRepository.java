package naf.norsys.reservation.repository;

import naf.norsys.reservation.model.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Long> {

}
