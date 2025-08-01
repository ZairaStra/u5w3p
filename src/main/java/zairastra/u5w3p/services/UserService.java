package zairastra.u5w3p.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zairastra.u5w3p.entities.User;
import zairastra.u5w3p.entities.enums.Role;
import zairastra.u5w3p.exceptions.BadRequestException;
import zairastra.u5w3p.exceptions.NotFoundException;
import zairastra.u5w3p.exceptions.UnauthorizedException;
import zairastra.u5w3p.payloads.NewUserDTO;
import zairastra.u5w3p.repositories.UsersRepository;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder bCrypt;

    public User saveUser(NewUserDTO payload) {
        usersRepository.findByEmailIgnoreCase(payload.email()).ifPresent(user -> {
            throw new BadRequestException("A user with email " + payload.email() + " already exists in our system");
        });
        usersRepository.findByUsernameIgnoreCase(payload.username()).ifPresent(user -> {
            throw new BadRequestException("A user with username " + payload.username() + " already exists in our system");
        });

        User newUser = new User(payload.name(), payload.surname(), payload.username(), payload.email(), bCrypt.encode(payload.password()));

        newUser.setRole(Role.SIMPLE_USER);

        User savedUser = usersRepository.save(newUser);
        log.info("The user " + payload.name() + " " + payload.surname() + " has been saved");

        return savedUser;
    }

    public User findUserById(Long userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }


    public User findUserByEmail(String email) {
        return usersRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

    public User findUserByUsername(String username) {
        return usersRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new NotFoundException("User with username " + username + " not found"));
    }

    public User findUserByIdAndVerifyRole(Long userId, Role requiredRole) {
        User u = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if (u.getRole() != requiredRole) {
            throw new UnauthorizedException("Unauthorized user - please select an EVENT_PLANNER user");
        }
        return u;
    }
}
