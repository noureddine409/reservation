package naf.norsys.reservation.model;

import jakarta.persistence.Embeddable;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Parameter {
    private String key;
    private String value;


}
