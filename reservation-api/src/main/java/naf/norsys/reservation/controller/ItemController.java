package naf.norsys.reservation.controller;


import naf.norsys.reservation.dto.ItemDto;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.service.GenericService;
import naf.norsys.reservation.utils.ClassTypeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController extends GenericController<Item, ItemDto> {

    public ItemController(ModelMapper modelMapper, ClassTypeProvider classTypeProvider, GenericService<Item> genericService) {
        super(modelMapper, classTypeProvider, genericService);
    }
}
