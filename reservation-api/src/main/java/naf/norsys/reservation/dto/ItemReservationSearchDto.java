package naf.norsys.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import naf.norsys.reservation.validation.ValidPeriod;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemReservationSearchDto {
    @NotNull
    private Long itemId;
    @NotNull
    @ValidPeriod
    private PeriodDto period;
}
