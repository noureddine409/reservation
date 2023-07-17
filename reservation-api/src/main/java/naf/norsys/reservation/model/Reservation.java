package naf.norsys.reservation.model;


import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Reservation extends GenericEntity{
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
