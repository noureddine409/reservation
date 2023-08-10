package naf.norsys.reservation.controller;


import jakarta.validation.Valid;
import naf.norsys.reservation.dto.ItemDto;
import naf.norsys.reservation.dto.SearchItemDto;
import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.ResourceOwnershipException;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.ItemService;
import naf.norsys.reservation.utils.AuthenticationHelper;
import naf.norsys.reservation.utils.MapHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static naf.norsys.reservation.common.CoreConstant.Exception.AUTHORIZATION_RESOURCE_OWNERSHIP;
import static naf.norsys.reservation.common.CoreConstant.Pagination.DEFAULT_PAGE_NUMBER;
import static naf.norsys.reservation.common.CoreConstant.Pagination.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    private final MapHelper mapHelper;

    private final AuthenticationHelper authenticationHelper;

    public ItemController(ItemService itemService, MapHelper mapHelper, AuthenticationHelper authenticationHelper) {
        this.itemService = itemService;
        this.mapHelper = mapHelper;
        this.authenticationHelper = authenticationHelper;
    }

    private boolean isNotOwner(Item item) {
        final Long currentUserId = authenticationHelper.getCurrentUserId();
        final Long resourceOwnerId = item.getCreatedBy().getId();
        return !currentUserId.equals(resourceOwnerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getById(@PathVariable("id") Long id) throws ElementNotFoundException {
        final Item item = itemService.findById(id);
        final ItemDto itemDto = mapHelper.convertToDto(item, ItemDto.class);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> update(@PathVariable("id") Long id, @Valid @RequestBody ItemDto dto) throws ElementNotFoundException {
        final Item item = itemService.findById(id);
        if (isNotOwner(item)) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }
        final Item entity = mapHelper.convertToEntity(dto, Item.class);
        final Item saved = itemService.update(id, entity);
        final ItemDto itemDto = mapHelper.convertToDto(saved, ItemDto.class);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) throws ElementNotFoundException {
        final Item item = itemService.findById(id);
        if (isNotOwner(item)) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }
        return new ResponseEntity<>(itemService.delete(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDto> save(@RequestBody @Valid ItemDto dto) throws ElementAlreadyExistsException {
        Item item = mapHelper.convertToEntity(dto, Item.class);
        final User currentUser = authenticationHelper.getCurrentUser();
        item.setCreatedBy(currentUser);
        final Item saved = itemService.save(item);
        final ItemDto itemDto = mapHelper.convertToDto(saved, ItemDto.class);
        return new ResponseEntity<>(itemDto, HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestBody SearchItemDto searchDto) throws BusinessException {
        searchDto.validate();
        final List<Item> items = itemService.search(searchDto.getKeyword(),
                searchDto.getCategory(),
                searchDto.getPage(),
                searchDto.getSize());
        final List<ItemDto> responseDto = mapHelper.convertListToDto(items, ItemDto.class);
        final int totalPages = itemService.getTotalPages(searchDto.getSize());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("Access-Control-Expose-Headers", "X-Total-Pages");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> findByUser(@RequestParam(value = "page", defaultValue = "" + DEFAULT_PAGE_NUMBER) Integer page,
                                                    @RequestParam(value = "size", defaultValue = "" + DEFAULT_PAGE_SIZE) Integer size
    ) throws BusinessException {

        List<Item> items = itemService.findByUser(authenticationHelper.getCurrentUserId(), page, size);
        final List<ItemDto> responseDto = mapHelper.convertListToDto(items, ItemDto.class);
        final int totalPages = itemService.getTotalPages(size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("Access-Control-Expose-Headers", "X-Total-Pages");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseDto);
    }
}
