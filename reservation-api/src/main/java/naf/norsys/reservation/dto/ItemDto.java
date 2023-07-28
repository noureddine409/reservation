package naf.norsys.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import naf.norsys.reservation.model.GenericEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class ItemDto extends GenericDto{
    @NotBlank
    private String name;

    @NotBlank
    private String description;
    @NotNull
    private GenericEnum.ItemCategory category;

    @NotNull
    private GenericEnum.ItemStatus status;

    private UserDto createdBy;

    @Builder
    public ItemDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String description, GenericEnum.ItemCategory category, GenericEnum.ItemStatus status, UserDto createdBy) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.createdBy = createdBy;
    }
}
