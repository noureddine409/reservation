package naf.norsys.reservation.service.impl;

import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.ItemAvailabilityException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.repository.GenericRepository;
import naf.norsys.reservation.repository.ItemRepository;
import naf.norsys.reservation.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static naf.norsys.reservation.model.GenericEnum.ItemStatus.UNAVAILABLE;

@Service
public class ItemServiceImpl extends GenericServiceImpl<Item> implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(GenericRepository<Item> genericRepository, ModelMapper modelMapper, ItemRepository itemRepository) {
        super(genericRepository, modelMapper);
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findByUser(Long userId, int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        return itemRepository.findAll(pageable).toList();
    }

    @Override
    public List<Item> search(String keyword, GenericEnum.ItemCategory category, int page, int size) throws BusinessException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return itemRepository.searchByKeywordAndCategory(keyword, category, pageable);
        } catch (DataAccessException e) {
            throw new BusinessException(null, e, CoreConstant.Exception.FIND_ELEMENTS, null);
        }
    }

    @Override
    public Item checkItemStatus(Long itemId) throws ItemAvailabilityException, ElementNotFoundException {
        final Item item = this.findById(itemId);
        if(UNAVAILABLE.equals(item.getStatus())){
            throw new ItemAvailabilityException(null, new ItemAvailabilityException(),
                    CoreConstant.Exception.INVALID_STATUS, new Object[]{itemId});
        }
        return item;
    }
}
