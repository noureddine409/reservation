package naf.norsys.reservation.repository;

import naf.norsys.reservation.model.Reservation;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static naf.norsys.reservation.model.GenericEnum.ItemStatus.AVAILABLE;

public interface ReservationRepository extends GenericRepository<Reservation> {

    default Specification<Reservation> existingReservations(Long itemId, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("item").get("id"), itemId),
                criteriaBuilder.or(
                        criteriaBuilder.between(root.get("startDate"), startDate, endDate),
                        criteriaBuilder.between(root.get("endDate"), startDate, endDate)
                )
        );
    }

    default Boolean checkAvailability(Long itemId, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<Reservation> reservationSpecification = existingReservations(itemId,
                startDate,
                endDate);
        return !exists(reservationSpecification);
    }
}
