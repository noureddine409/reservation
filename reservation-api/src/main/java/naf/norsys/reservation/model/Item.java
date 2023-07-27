package naf.norsys.reservation.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Item extends GenericEntity {

    private String name;
    private String description;
    private GenericEnum.ItemCategory category;
    private GenericEnum.ItemStatus status;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<EvaluationComment> evaluationComments;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Builder
    public Item(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String description, GenericEnum.ItemCategory category, User createdBy, GenericEnum.ItemStatus status) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdBy = createdBy;
        this.status=status;
    }
}
