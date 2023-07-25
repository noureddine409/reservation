package naf.norsys.reservation.model;


import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Item extends GenericEntity {

    private String name;
    private String description;
    private GenericEnum.ItemCategory category;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Builder
    public Item(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String description, GenericEnum.ItemCategory category, User createdBy) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdBy = createdBy;
    }
}
