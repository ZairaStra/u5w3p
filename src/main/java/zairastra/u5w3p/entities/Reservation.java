package zairastra.u5w3p.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Please insert a valid User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @NotNull(message = "Please insert a valid Event")
    private Event event;

    public Reservation(User user, Event event) {
        this.user = user;
        this.event = event;
    }
}
