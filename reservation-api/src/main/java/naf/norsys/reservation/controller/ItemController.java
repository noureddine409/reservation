package naf.norsys.reservation.controller;


import io.swagger.annotations.Api;
import naf.norsys.reservation.dto.ItemDto;
import naf.norsys.reservation.dto.SearchItemDto;
import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.service.GenericService;
import naf.norsys.reservation.service.ItemService;
import naf.norsys.reservation.utils.ClassTypeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@Api(tags = "Item Management", description = "Web services for item (product) Management")

public class ItemController extends GenericController<Item, ItemDto> {

    private final ItemService itemService;

    public ItemController(ModelMapper modelMapper, ClassTypeProvider classTypeProvider, GenericService<Item> genericService, ItemService itemService) {
        super(modelMapper, classTypeProvider, genericService);
        this.itemService = itemService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestBody SearchItemDto searchDto) throws BusinessException {
        searchDto.validate();
        final List<Item> items = itemService.search(searchDto.getKeyword(),
                searchDto.getCategory(),
                searchDto.getPage(),
                searchDto.getSize());
        final List<ItemDto> responseDto = convertListToDto(items);
        final int totalPages = itemService.getTotalPages(searchDto.getSize());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("Access-Control-Expose-Headers", "X-Total-Pages");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseDto);
    }
    @GetMapping
    public ResponseEntity<List<ItemDto>> findByUser(@RequestParam int page, int size) throws BusinessException {
        List<Item> items = itemService.findByUser(null, page, size);
        return new ResponseEntity<>(convertListToDto(items), HttpStatus.OK);
    }





}
