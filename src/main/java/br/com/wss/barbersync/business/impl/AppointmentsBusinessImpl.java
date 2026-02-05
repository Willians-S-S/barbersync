package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.AppointmentsBusiness;
import br.com.wss.barbersync.entities.Appointments;
import br.com.wss.barbersync.repositories.AppointmentsRepository;
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
public class AppointmentsBusinessImpl extends AbstractBusinessImpl<Appointments, String> implements AppointmentsBusiness {

    @Getter
    private final AppointmentsRepository repository;

}
