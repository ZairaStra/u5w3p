package zairastra.u5w3p.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewEventDTO(
        @NotEmpty(message = "The event must have a name")
        String title,
        String description,
        @NotNull(message = "Please select a date for the event")
        LocalDate date,
        @NotEmpty(message = "A place for the event is required")
        String place,

        int maxNumbParticipant,

        @NotNull(message = "EventPlanner ID is required")
        Long eventPlannerId) {
}
