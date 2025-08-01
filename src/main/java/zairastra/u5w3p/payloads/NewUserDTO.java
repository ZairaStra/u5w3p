package zairastra.u5w3p.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(

        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Surname is required")
        String surname,
        @NotEmpty(message = "Username is required")
        String username,
        @Email
        @NotEmpty(message = "Email is required")
        String email,
        @NotEmpty(message = "Password is required")
        @Size(min = 6)
        String password,
        @NotEmpty(message = "Role is required")
        String role) {
}
