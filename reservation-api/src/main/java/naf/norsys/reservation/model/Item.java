package naf.norsys.reservation.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<EvaluationComment> evaluationComments;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @ElementCollection
    @CollectionTable(name = "item_parameters", joinColumns = @JoinColumn(name = "item_id"))
    private List<Parameter> params;

    @Builder
    public Item(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String description, GenericEnum.ItemCategory category, User createdBy,
                GenericEnum.ItemStatus status, List<Parameter> params) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdBy = createdBy;
        this.status=status;
        this.params = params;
    }
}
