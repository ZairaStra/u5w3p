package zairastra.u5w3p.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import zairastra.u5w3p.entities.enums.Role;

import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Surname is required")
    private String surname;
    @NotEmpty(message = "Username is required")
    private String username;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String surname, String username, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}
