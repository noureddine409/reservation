package naf.norsys.reservation.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
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
