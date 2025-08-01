package zairastra.u5w3p.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotEmpty(message = "The event must have a name")
    private String title;

    private String description;
    @NotNull(message = "Please select a date for the event")
    private LocalDate date;
    @NotEmpty(message = "A place for the event is required")
    private String place;
    @Min(value = 2, message = "To create an event, a minimum number of two participants must be expected")
    private int maxNumbParticipant;

    @NotNull(message = "Please insert a valid EventPlanner")
    @ManyToOne
    @JoinColumn(name = "event_planner_id", nullable = false)
    private User eventPlanner;

    @OneToMany(mappedBy = "event")
    private List<Reservation> reservations;


    public Event(String title, String description, LocalDate date, String place, int maxNumbParticipant, User eventPlanner) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.place = place;
        this.maxNumbParticipant = maxNumbParticipant;
        this.eventPlanner = eventPlanner;
    }
}
