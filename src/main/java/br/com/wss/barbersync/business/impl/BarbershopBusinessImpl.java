package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.BarbershopBusiness;
import br.com.wss.barbersync.entities.Barbershop;
import br.com.wss.barbersync.repositories.BarbershopRepository;
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
public class BarbershopBusinessImpl extends AbstractBusinessImpl<Barbershop, String> implements BarbershopBusiness {

    @Getter
    private final BarbershopRepository repository;

}
