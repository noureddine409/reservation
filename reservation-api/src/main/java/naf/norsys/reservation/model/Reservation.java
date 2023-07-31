package naf.norsys.reservation.model;


import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation extends GenericEntity {

    @Embedded
    private ReservationPeriod period;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public Reservation(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, ReservationPeriod period, User user, Item item) {
        super(id, createdAt, updatedAt);
        this.period = period;
        this.user = user;
        this.item = item;
    }
}

