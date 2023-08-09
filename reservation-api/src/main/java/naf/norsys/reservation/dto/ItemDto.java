package naf.norsys.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Parameter;

import java.time.LocalDateTime;
import java.util.List;


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

    private List<ParameterDto> params;


    @Builder
    public ItemDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String description, GenericEnum.ItemCategory category, GenericEnum.ItemStatus status, UserDto createdBy, List<ParameterDto> params) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.createdBy = createdBy;
        this.params=params;
    }
}
