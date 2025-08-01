package zairastra.u5w3p.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zairastra.u5w3p.entities.Event;
import zairastra.u5w3p.entities.User;
import zairastra.u5w3p.entities.enums.Role;
import zairastra.u5w3p.exceptions.BadRequestException;
import zairastra.u5w3p.exceptions.NotFoundException;
import zairastra.u5w3p.exceptions.UnauthorizedException;
import zairastra.u5w3p.payloads.NewEventDTO;
import zairastra.u5w3p.repositories.EventsRepository;

import java.time.LocalDate;

@Service
@Slf4j
public class EventService {

    @Autowired
    EventsRepository eventsRepository;

    @Autowired
    UserService userService;

    //metodi per creare, modificare, eliminare - solo organizzatori, solo lo stesso organizzatore
    public Event saveEvent(NewEventDTO payload, User user) {
        eventsRepository.findByDateAndTitleIgnoreCaseAndPlaceIgnoreCase(payload.date(), payload.title(), payload.place()).ifPresent(event -> {
            throw new BadRequestException("An event named " + payload.title() + " in " + payload.place() + " on " + payload.date() + " is already scheduled");
        });

        if (payload.date().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot schedule an event in the past");
        }

        User eventPlanner = userService.findUserByIdAndVerifyRole(payload.eventPlannerId(), Role.EVENT_PLANNER);

        Event newEvent = new Event(payload.title(), payload.description(), payload.date(), payload.place(), payload.maxNumbParticipant(), eventPlanner);
        Event savedEvent = eventsRepository.save(newEvent);

        log.info("An event named " + payload.title() + " in " + payload.place() + " on " + payload.date() + " has been scheduled");

        return savedEvent;
    }

    public Event findEventById(Long eventId) {
        return eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));
    }

    public Event findEventByIdAndUpdate(Long eventId, NewEventDTO payload, Long currentUserId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));

        if (!event.getEventPlanner().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not authorized to update this event");
        }

        if (payload.date().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot schedule an event in the past");
        }

        event.setTitle(payload.title());
        event.setDescription(payload.description());
        event.setDate(payload.date());
        event.setPlace(payload.place());
        event.setMaxNumbParticipant(payload.maxNumbParticipant());

        Event updatedEvent = eventsRepository.save(event);

        log.info("Event with id " + eventId + " updated");

        return updatedEvent;
    }

    public void findEventByIdAndDelete(Long eventId, Long currentUserId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));

        if (!event.getEventPlanner().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not authorized to delete this event");
        }

        eventsRepository.delete(event);

        log.info("Event with id " + eventId + " deleted");
    }

    //trovare tutti gli eventi?
    public Page<Event> findAll(int pageNumb, int pageSize) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumb, pageSize);
        return eventsRepository.findAll(pageable);
    }

}
