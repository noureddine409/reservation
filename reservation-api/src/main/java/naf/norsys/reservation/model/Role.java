package naf.norsys.reservation.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Role extends GenericEntity {

    @Enumerated(EnumType.STRING)
    private GenericEnum.RoleName name;

     @OneToMany(mappedBy = "role")
     private List<User> users;

}


