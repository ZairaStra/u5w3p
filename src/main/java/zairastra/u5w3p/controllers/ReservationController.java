package zairastra.u5w3p.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zairastra.u5w3p.entities.Reservation;
import zairastra.u5w3p.entities.User;
import zairastra.u5w3p.exceptions.ValidationException;
import zairastra.u5w3p.payloads.NewReservationDTO;
import zairastra.u5w3p.payloads.ReservationResponseDTO;
import zairastra.u5w3p.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDTO createReservation(@RequestBody @Validated NewReservationDTO payload, BindingResult validationResult, @AuthenticationPrincipal User authenticatedUser) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        Reservation newReservation = reservationsService.saveReservation(payload, authenticatedUser);
        return new ReservationResponseDTO(newReservation.getId());
    }

}
