package naf.norsys.reservation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Role extends GenericEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private GenericEnum.RoleName name;



}


