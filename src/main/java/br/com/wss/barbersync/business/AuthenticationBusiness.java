package br.com.wss.barbersync.business;

import br.com.wss.barbersync.dtos.JwtRequestDTO;
import br.com.wss.barbersync.dtos.JwtResponseDTO;
import br.com.wss.base.BaseBusiness;

public interface AuthenticationBusiness {

    public JwtResponseDTO authentication(final JwtRequestDTO authenticationRequest);
}
