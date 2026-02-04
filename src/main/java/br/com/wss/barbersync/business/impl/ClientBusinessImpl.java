package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.ClientBusiness;
import br.com.wss.barbersync.entities.Client;
import br.com.wss.barbersync.repositories.ClientRepository;
import br.com.wss.base.AbstractBusinessImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Slf4j
@AllArgsConstructor
public class ClientBusinessImpl extends AbstractBusinessImpl<Client, String> implements ClientBusiness {

    @Getter
    private final ClientRepository repository;

}
