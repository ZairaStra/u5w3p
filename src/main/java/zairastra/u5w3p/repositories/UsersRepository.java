package zairastra.u5w3p.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zairastra.u5w3p.entities.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByUsernameIgnoreCase(String username);
}
