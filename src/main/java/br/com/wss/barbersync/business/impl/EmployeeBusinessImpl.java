package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.*;
import br.com.wss.barbersync.entities.Appointments;
import br.com.wss.barbersync.entities.Employee;
import br.com.wss.barbersync.entities.Services;
import br.com.wss.barbersync.repositories.EmployeeRepository;
import br.com.wss.base.AbstractBusinessImpl;
import br.com.wss.base.TransactionType;
import br.com.wss.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@Slf4j
@AllArgsConstructor(onConstructor_ =  @Lazy)
public class EmployeeBusinessImpl extends AbstractBusinessImpl<Employee, String> implements EmployeeBusiness {

    @Getter
    private final EmployeeRepository repository;

    @Lazy
    private final AccountBusiness accountBusiness;

    private final BarbershopBusiness barbershopBusiness;

    private final ServicesBusiness servicesBusiness;

    private final AppointmentsBusiness appointmentsBusiness;

    @Override
    public Employee insert(Employee entity) {
        setDependencies(entity, TransactionType.INSERT);
        return super.insert(entity);
    }

    @Override
    protected Employee setDependencies(final Employee entity, final TransactionType transactionType) {
        if (entity.getAccount() != null)
            accountBusiness.findDependencyByUid(entity.getAccount().getUid())
                    .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Account not found"));

        if (entity.getBarbershop() != null)
            barbershopBusiness.findDependencyByUid(entity.getBarbershop().getUid())
                    .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Barbershop not found"));

        if (!entity.getServices().isEmpty()) {
            List<Services> services = entity.getServices()
                    .stream()
                    .filter(service ->
                            servicesBusiness
                                    .findDependencyByUid(service.getUid()).isPresent()).toList();

            entity.setServices(services);
        }

        if (entity.getAppointments() != null) {
            List<Appointments> appointments = entity.getAppointments()
                    .stream().filter(appointment ->
                            appointmentsBusiness.findDependencyByUid(appointment.getUid()).isPresent())
                    .toList();

            entity.setAppointments(appointments);
        }

        return entity;
    }

}
