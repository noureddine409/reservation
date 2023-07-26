package naf.norsys.reservation.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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


