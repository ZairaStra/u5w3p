package zairastra.u5w3p.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zairastra.u5w3p.entities.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, Long> {

    //elenco prenotazioni per singolo utente (extra)
    List<Reservation> findByUserId(Long userId);

    //no prenotazionii doppie per lo stesso evento
    Optional<Reservation> findByUserIdAndEventId(Long userId, Long eventId);

    //numero di prenotazioni per evento
    long countReservationsByEventId(Long eventId);
}
