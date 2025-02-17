package naf.norsys.reservation.repository;

import jakarta.persistence.criteria.Predicate;
import naf.norsys.reservation.model.GenericEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    default Specification<T> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // loop through all attributes of the entity and check if the attribute is a string
            // if it is, add a "like" predicate for that attribute
            for (Field field : root.getJavaType().getDeclaredFields()) {
                if (field.getType().equals(String.class)) {
                    predicates.add(cb.like(cb.lower(root.get(field.getName())), "%" + keyword.toLowerCase() + "%"));
                }
            }
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

}
