package naf.norsys.reservation.model;


import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.*;
import java.util.List;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Item extends GenericEntity {

    private String name;
    private String description;
    private GenericEnum.ItemCategory category;
    private GenericEnum.ItemStatus itemStatus;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<EvaluationComment> evaluationComments;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Builder
    public Item(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String description, GenericEnum.ItemCategory category, User createdBy) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdBy = createdBy;
    }
}
