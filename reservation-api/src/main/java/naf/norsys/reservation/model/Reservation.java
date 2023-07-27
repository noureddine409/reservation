package naf.norsys.reservation.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation extends GenericEntity{
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public Reservation(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime startDate, LocalDateTime endDate, User user, Item item) {
        super(id, createdAt, updatedAt);
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.item = item;
    }
}

