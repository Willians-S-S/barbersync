package br.com.wss.barbersync.resources;

import br.com.wss.barbersync.business.AuthenticationBusiness;
import br.com.wss.barbersync.dtos.JwtRequestDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthenticationResource {

    private final AuthenticationBusiness business;

    public static final String AUTHENTICATE = "/authenticate";

    @PostMapping(AUTHENTICATE)
    public ResponseEntity<?> login(final JwtRequestDTO authenticationRequest){
        return ResponseEntity.ok(business.authentication(authenticationRequest));
    }
}
