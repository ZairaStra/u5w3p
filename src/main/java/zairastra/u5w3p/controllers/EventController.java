package zairastra.u5w3p.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zairastra.u5w3p.entities.Event;
import zairastra.u5w3p.entities.User;
import zairastra.u5w3p.exceptions.ValidationException;
import zairastra.u5w3p.payloads.EventResponseDTO;
import zairastra.u5w3p.payloads.NewEventDTO;
import zairastra.u5w3p.services.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EVENT_PLANNER')")
    //devo verificare che l'id dell'event planner sia quello dell'utente loggato
    //me lo passo dall'utente autenticato verificato
    public EventResponseDTO createEvent(@RequestBody @Validated NewEventDTO payload, BindingResult validationResult, @AuthenticationPrincipal User authenticatedUser) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }


        Event newEvent = eventService.saveEvent(payload, authenticatedUser);
        return new EventResponseDTO(newEvent.getId());
    }

    @PreAuthorize("hasAuthority('EVENT_PLANNER')")
    @PutMapping("/{eventId}")
    public Event getEventByIdAndUpdate(@PathVariable Long eventId, @RequestBody @Validated NewEventDTO payload, BindingResult validationResult, @AuthenticationPrincipal User authenticatedUser) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return eventService.findEventByIdAndUpdate(eventId, payload, authenticatedUser.getId());
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('EVENT_PLANNER')")
    public void getEventByIdAndDelete(@PathVariable Long eventId, @AuthenticationPrincipal User authenticatedUser) {
        eventService.findEventByIdAndDelete(eventId, authenticatedUser.getId());
    }
}
