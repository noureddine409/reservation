package naf.norsys.reservation.model;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

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

