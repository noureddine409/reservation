package naf.norsys.reservation.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ReservationPeriod {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
