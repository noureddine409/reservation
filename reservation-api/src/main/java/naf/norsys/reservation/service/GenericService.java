package naf.norsys.reservation.service;

import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.GenericEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<T extends GenericEntity> {

    T update(final Long id, final T entity) throws ElementNotFoundException;

    T findById(final Long id) throws ElementNotFoundException;

    T save(final T entity) throws ElementAlreadyExistsException;

    List<T> search(String keyword, Pageable pageable) throws BusinessException;

    long countAll();

    boolean delete(final Long id) throws ElementNotFoundException;

    List<T> findAll() throws BusinessException;

}
