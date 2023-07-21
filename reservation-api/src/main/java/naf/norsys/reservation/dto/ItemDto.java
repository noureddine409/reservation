package naf.norsys.reservation.dto;

import lombok.*;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.User;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto extends GenericDto{
    @NotBlank
    private String name;
    private String description;
    private GenericEnum.ItemCategory category;

    private User createdBy;
}
