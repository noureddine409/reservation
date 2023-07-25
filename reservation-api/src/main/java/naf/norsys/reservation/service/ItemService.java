package naf.norsys.reservation.service;

import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.model.GenericEnum.ItemCategory;
import naf.norsys.reservation.model.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService extends GenericService<Item> {

    List<Item> search(String keyword, ItemCategory category, int page, int size) throws BusinessException;

}
