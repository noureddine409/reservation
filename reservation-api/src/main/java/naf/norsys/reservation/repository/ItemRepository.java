package naf.norsys.reservation.repository;


import jakarta.persistence.criteria.Predicate;
import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Item;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface ItemRepository extends GenericRepository<Item> {

    List<Item> findByCreatedBy_id(Long userId, Pageable pageable) ;
    default List<Item> searchByKeywordAndCategory(String keyword, GenericEnum.ItemCategory category, Pageable pageable) {
        Specification<Item> spec = Specification.where(hasKeyword(keyword))
                .and((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (Objects.nonNull(category)) {
                        predicates.add(cb.equal(root.get("category"), category));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                });
        if(pageable.isUnpaged()) {
            pageable = PageRequest.of(CoreConstant.Pagination.DEFAULT_PAGE_NUMBER, CoreConstant.Pagination.DEFAULT_PAGE_SIZE, Sort.by(CoreConstant.Pagination.DEFAULT_SORT_PROPERTY).descending());
        }
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(CoreConstant.Pagination.DEFAULT_SORT_PROPERTY).descending());
        }
        return findAll(spec, pageable).getContent();
    }
}
