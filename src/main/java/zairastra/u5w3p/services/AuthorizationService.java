package zairastra.u5w3p.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zairastra.u5w3p.entities.User;
import zairastra.u5w3p.exceptions.UnauthorizedException;
import zairastra.u5w3p.payloads.LoginDTO;
import zairastra.u5w3p.tools.JWTTools;

@Service
public class AuthorizationService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bCrypt;

    public String checkEmailBeforeLogin(LoginDTO payload) {
        User found = userService.findUserByEmail(payload.email());
        if (bCrypt.matches(payload.password(), found.getPassword())) {
            String extractedToken = jwtTools.createToken(found);
            return extractedToken;
        } else {
            throw new UnauthorizedException("Unauthorized - try again");
        }
    }
}
