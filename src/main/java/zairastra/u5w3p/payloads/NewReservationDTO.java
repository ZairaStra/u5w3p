package zairastra.u5w3p.payloads;

import jakarta.validation.constraints.NotNull;

public record NewReservationDTO(
        @NotNull(message = "User ID is required")
        Long userId,
        @NotNull(message = "Event ID is required")
        Long eventId
) {
}
