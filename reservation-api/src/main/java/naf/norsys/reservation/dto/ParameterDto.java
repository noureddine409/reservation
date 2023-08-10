package naf.norsys.reservation.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParameterDto {
    private String key;
    private String value;
}
