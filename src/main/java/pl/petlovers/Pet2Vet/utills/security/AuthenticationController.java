package pl.petlovers.Pet2Vet.utills.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin
public class AuthenticationController {

    @Secured({"ROLE_ADMIN", "ROLE_OWNER", "ROLE_VET", "ROLE_KEEPER"})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public HttpStatus checkAuthentication() {
        return HttpStatus.OK;
    }

}
