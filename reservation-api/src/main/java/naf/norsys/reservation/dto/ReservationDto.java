package naf.norsys.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import naf.norsys.reservation.validation.AfterCurrentDate;
import naf.norsys.reservation.validation.ValidPeriod;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDto extends GenericDto {
    @ValidPeriod
    @AfterCurrentDate
    private PeriodDto period;
    private ItemDto item;
    private UserDto user;

    @Builder
    public ReservationDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, PeriodDto period, ItemDto item, UserDto user) {
        super(id, createdAt, updatedAt);
        this.period = period;
        this.item = item;
        this.user = user;
    }
}
