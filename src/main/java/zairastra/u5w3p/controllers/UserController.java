package zairastra.u5w3p.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zairastra.u5w3p.entities.User;
import zairastra.u5w3p.exceptions.ValidationException;
import zairastra.u5w3p.payloads.NewUserDTO;
import zairastra.u5w3p.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated NewUserDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return userService.saveUser(payload);
    }

    @GetMapping("/me")
    public User getMyProfile(@AuthenticationPrincipal User authorizedUser) {
        return authorizedUser;
    }
}
