package naf.norsys.reservation.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    protected List<D> convertListToDto(List<T> source, Class<D> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }


    @PostMapping
    public ResponseEntity<D> save(@RequestBody D dto) throws ElementAlreadyExistsException {
        return null;
    }

    @PostMapping("/search")
    public ResponseEntity<Page<D>> getAll(@RequestBody SearchDto dto) throws BusinessException {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable("id") Long id) throws ElementNotFoundException {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable("id") Long id, @RequestBody D dto) throws ElementNotFoundException {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) throws ElementNotFoundException {
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<D> patch(@PathVariable(name = "id") Long id,
                                   @RequestBody D dto) throws ElementNotFoundException {
        return null;
    }


}
