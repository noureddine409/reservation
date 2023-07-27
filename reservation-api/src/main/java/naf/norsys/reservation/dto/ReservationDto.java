package naf.norsys.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto extends GenericDto {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;
    private ItemDto item;
    private UserDto user;

    @Builder
    public ReservationDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime startDate, LocalDateTime endDate, ItemDto item, UserDto user) {
        super(id, createdAt, updatedAt);
        this.startDate = startDate;
        this.endDate = endDate;
        this.item = item;
        this.user = user;
    }
}
