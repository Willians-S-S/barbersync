package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.ServicesBusiness;
import br.com.wss.barbersync.entities.Services;
import br.com.wss.barbersync.repositories.ServicesRepository;
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
public class ServicesBusinessImpl extends AbstractBusinessImpl<Services, String> implements ServicesBusiness {

    @Getter
    private final ServicesRepository repository;

}
