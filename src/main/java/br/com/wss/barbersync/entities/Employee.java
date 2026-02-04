package br.com.wss.barbersync.entities;

import br.com.wss.barbersync.enums.EmployeeEnum;
import br.com.wss.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
//@Table(schema = GenericUtils.SCHEMA)
@Table(name = "EMPLOYEE")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "uid")
public class Employee extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Barbershop barbershop;

    private BigDecimal commissionRate;

    @Enumerated(EnumType.STRING)
    private EmployeeEnum roleEmployee;

    //criar uma lista de Enum dos dias da semanas que trabalha

    LocalTime startTime;
    LocalTime endTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "EMPLOYEE_SERVICE",
            joinColumns = @JoinColumn(name = "services_uid"),
            inverseJoinColumns = @JoinColumn(name = "employees_uid"))
    List<Services>  services;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    List<Appointments> appointments;
}
