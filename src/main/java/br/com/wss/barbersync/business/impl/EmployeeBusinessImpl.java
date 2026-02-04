package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.EmployeeBusiness;
import br.com.wss.barbersync.entities.Employee;
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
public class EmployeeBusinessImpl extends AbstractBusinessImpl<Employee, String> implements EmployeeBusiness {

    @Getter
    private final ClientRepository repository;

}
