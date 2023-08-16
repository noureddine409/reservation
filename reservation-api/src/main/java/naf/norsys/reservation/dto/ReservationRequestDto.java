package naf.norsys.reservation.dto;


import lombok.*;
import naf.norsys.reservation.validation.AfterCurrentDate;
import naf.norsys.reservation.validation.ValidPeriod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    @ValidPeriod
    @AfterCurrentDate
    private PeriodDto period;

    private Long itemId;

}
