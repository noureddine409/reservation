package naf.norsys.reservation.dto;

import lombok.*;
import naf.norsys.reservation.validation.ValidPeriod;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReservationSearchDto {
    @NotNull
    private Long userId;
    @NotNull
    @ValidPeriod
    private PeriodDto period;
}
