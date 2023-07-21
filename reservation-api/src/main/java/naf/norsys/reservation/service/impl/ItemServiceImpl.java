package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends GenericServiceImpl<Item> implements ItemService {

    public ItemServiceImpl(GenericRepository<Item> genericRepository, ModelMapper modelMapper) {
        super(genericRepository, modelMapper);
    }
}
