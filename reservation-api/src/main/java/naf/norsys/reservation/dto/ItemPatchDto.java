package naf.norsys.reservation.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import naf.norsys.reservation.model.GenericEnum;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPatchDto {

    private String name;

    private String description;
    private GenericEnum.ItemCategory category;

    private GenericEnum.ItemStatus status;

    private List<ParameterDto> params;

}
