package zairastra.u5w3p.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zairastra.u5w3p.payloads.LoginDTO;
import zairastra.u5w3p.payloads.LoginResponseDTO;
import zairastra.u5w3p.services.AuthorizationService;
import zairastra.u5w3p.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    public UserService userService;

    @Autowired
    public AuthorizationService authorizationsService;

    //LOGIN
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO payload) {
        String extractedToken = authorizationsService.checkEmailBeforeLogin(payload);
        return new LoginResponseDTO(extractedToken);
    }
}
