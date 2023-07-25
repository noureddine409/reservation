package naf.norsys.reservation.service;

import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.GenericEntity;

public interface GenericService<T extends GenericEntity> {

    T update(final Long id, final T entity) throws ElementNotFoundException;

    T findById(final Long id) throws ElementNotFoundException;

    T save(final T entity) throws ElementAlreadyExistsException;
    
    boolean delete(final Long id) throws ElementNotFoundException;

    int getTotalPages(final int pageSize);
}
