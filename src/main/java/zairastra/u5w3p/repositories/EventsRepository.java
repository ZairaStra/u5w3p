package zairastra.u5w3p.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zairastra.u5w3p.entities.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByDateAndTitleIgnoreCaseAndPlaceIgnoreCase(LocalDate date, String title, String place);

    //gli utenti dovrebbero poter cercare per data o per luogo?;
    List<Event> findByDate(LocalDate date);

    List<Event> findByPlace(String place);

    //ogni organizzatore dovrebbe poter accedere solo alla lista dei propri eventi per modificarli/eliminarlii
    //TODO: ricordati di consentire agli organizzatori di prenotare solo gli eventi degli altri
    List<Event> findByEventPlannerId(Long eventPlannerId);
}
