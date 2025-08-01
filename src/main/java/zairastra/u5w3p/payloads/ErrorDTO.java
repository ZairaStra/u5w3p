package zairastra.u5w3p.payloads;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime stamp) {
}
