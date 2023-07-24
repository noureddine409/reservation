package naf.norsys.reservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import naf.norsys.reservation.dto.GenericDto;
import naf.norsys.reservation.dto.SearchDto;
import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.GenericEntity;
import naf.norsys.reservation.service.GenericService;
import naf.norsys.reservation.utils.ClassTypeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public abstract class GenericController <T extends GenericEntity, D extends GenericDto> {
    private final ModelMapper modelMapper;
    private final ClassTypeProvider classTypeProvider;

    private final GenericService<T> genericService;

    public GenericController(ModelMapper modelMapper, ClassTypeProvider classTypeProvider, GenericService<T> genericService) {
        this.modelMapper = modelMapper;
        this.classTypeProvider = classTypeProvider;
        this.genericService = genericService;
    }

    protected Class<?>[] getClasses(){
        return classTypeProvider.getClasses(this, GenericController.class);
    }
    @SuppressWarnings("unchecked")
    protected T convertToEntity(D dto) {
        return (T) modelMapper.map(dto, getClasses()[0]);
    }

    @SuppressWarnings("unchecked")
    protected D convertToDto(T entity) {
        return (D) modelMapper.map(entity, getClasses()[1]);
    }

    @PostMapping
    public ResponseEntity<D> save(@Valid @RequestBody D dto) throws ElementAlreadyExistsException {
        T entity = convertToEntity(dto);
        entity.setId(null);
        T savedEntity = genericService.save(entity);
        return new ResponseEntity<>(convertToDto(savedEntity), HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<D>> getAll(@RequestBody SearchDto dto) throws BusinessException {
        // TODO add search end point logic
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable("id") Long id) throws ElementNotFoundException {
        return new ResponseEntity<>(convertToDto(genericService.findById(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable("id") Long id, @Valid @RequestBody D dto) throws ElementNotFoundException {
        dto.setId(id);
        T entity = genericService.update(id, convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(entity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) throws ElementNotFoundException {
        return new ResponseEntity<>(genericService.delete(id), HttpStatus.OK);
    }



}
