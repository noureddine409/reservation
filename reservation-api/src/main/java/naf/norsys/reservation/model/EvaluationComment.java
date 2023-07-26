package naf.norsys.reservation.model;

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
    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}
