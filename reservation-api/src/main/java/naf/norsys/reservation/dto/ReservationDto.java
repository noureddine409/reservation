package naf.norsys.reservation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto extends GenericDto{
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
