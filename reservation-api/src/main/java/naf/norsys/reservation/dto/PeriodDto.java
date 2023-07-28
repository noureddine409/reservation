package naf.norsys.reservation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodDto {
    private LocalDateTime start;
    private LocalDateTime end;
}
