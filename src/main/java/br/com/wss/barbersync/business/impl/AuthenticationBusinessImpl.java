package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.AccountBusiness;
import br.com.wss.barbersync.business.AuthenticationBusiness;
import br.com.wss.barbersync.dtos.JwtRequestDTO;
import br.com.wss.barbersync.dtos.JwtResponseDTO;
import br.com.wss.barbersync.entities.Account;
import br.com.wss.filters.JwtToken;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class AuthenticationBusinessImpl implements AuthenticationBusiness {


    private AccountBusiness accountBusiness;

    private PasswordEncoder passwordEncoder;

    private JwtToken jwtToken;

    private static final String INVALID_CREDENTIALS = "Credenciais inválidas";

    @Override
    public JwtResponseDTO authentication(JwtRequestDTO authenticationRequest) {

        Account account = accountBusiness.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException(INVALID_CREDENTIALS));

        log.info(account.getUid());

        if (!passwordEncoder.matches(authenticationRequest.getPassword(), account.getPassword()))
            throw new BadCredentialsException(INVALID_CREDENTIALS);

        log.info(account.getPassword());

        return new JwtResponseDTO(jwtToken.genereteToken(account));
    }
}
