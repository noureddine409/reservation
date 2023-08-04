package naf.norsys.reservation.repository;

import jakarta.persistence.criteria.Join;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.model.ReservationPeriod;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public interface ReservationRepository extends GenericRepository<Reservation> {

    default Specification<Reservation> existingReservationsByItem(Long itemId, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            // Join the "period" embeddable to access its fields
            Join<Reservation, ReservationPeriod> periodJoin = root.join("period");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("item").get("id"), itemId),
                    criteriaBuilder.or(
                            criteriaBuilder.between(periodJoin.get("startDate"), startDate, endDate),
                            criteriaBuilder.between(periodJoin.get("endDate"), startDate, endDate)
                    )
            );
        };
    }
    default Boolean checkAvailability(Long itemId, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<Reservation> reservationSpecification = existingReservationsByItem(itemId,
                startDate,
                endDate);
        return !exists(reservationSpecification);
    }

    default Specification<Reservation> existingReservationsByUser(Long userId, LocalDateTime startDate, LocalDateTime endDate){
        return (root, query, criteriaBuilder) -> {
            // Join the "period" embeddable to access its fields
            Join<Reservation, ReservationPeriod> periodJoin = root.join("period");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("user").get("id"), userId),
                    criteriaBuilder.or(
                            criteriaBuilder.between(periodJoin.get("startDate"), startDate, endDate),
                            criteriaBuilder.between(periodJoin.get("endDate"), startDate, endDate)
                    )
            );
        };
    }
}
