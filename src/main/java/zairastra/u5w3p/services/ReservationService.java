package zairastra.u5w3p.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zairastra.u5w3p.entities.Event;
import zairastra.u5w3p.entities.Reservation;
import zairastra.u5w3p.entities.User;
import zairastra.u5w3p.exceptions.BadRequestException;
import zairastra.u5w3p.payloads.NewReservationDTO;
import zairastra.u5w3p.repositories.ReservationsRepository;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    public Reservation saveReservation(NewReservationDTO payload, User user) {

        User u = userService.findUserById(payload.userId());

        Event e = eventService.findEventById(payload.eventId());

        long reservationsNumb = reservationsRepository.countReservationsByEventId(payload.eventId());
        if (reservationsNumb >= e.getMaxNumbParticipant()) {
            throw new BadRequestException("This event is sold out");
        }

        reservationsRepository.findByUserIdAndEventId(u.getId(), e.getId()).ifPresent(reservation -> {
            throw new BadRequestException("User " + u.getName() + " " + u.getSurname() + " already has a reservation for the event " + e.getTitle());
        });

        Reservation r = new Reservation(u, e);
        Reservation savedReservation = reservationsRepository.save(r);

        log.info("A reservation has been created for the user " + u.getName() + " " + u.getSurname() + "for the event " + e.getTitle());

        return savedReservation;

    }
}
