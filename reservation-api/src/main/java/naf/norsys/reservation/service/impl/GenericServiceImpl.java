package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.GenericEntity;
import naf.norsys.reservation.service.GenericService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericServiceImpl<T extends GenericEntity> implements GenericService<T> {
    @Override
    public T update(Long id, T entity) throws ElementNotFoundException {
        return null;
    }

    @Override
    public T findById(Long id) throws ElementNotFoundException {
        return null;
    }

    @Override
    public T save(T entity) throws ElementAlreadyExistsException {
        return null;
    }

    @Override
    public List<T> search(String keyword, Pageable pageable) throws BusinessException {
        return null;
    }

    @Override
    public long countAll() {
        return 0;
    }

    @Override
    public boolean delete(Long id) throws ElementNotFoundException {
        return false;
    }

    @Override
    public List<T> findAll() throws BusinessException {
        return null;
    }
}
