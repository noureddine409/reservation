package naf.norsys.reservation.model;

import javax.persistence.Entity;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class EvaluationComment extends GenericEntity {

    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
