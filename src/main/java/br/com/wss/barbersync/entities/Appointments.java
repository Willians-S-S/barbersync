package br.com.wss.barbersync.entities;

import br.com.wss.barbersync.enums.AppointmentsStatusEnum;
import br.com.wss.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
//@Table(schema = GenericUtils.SCHEMA)
@Table(name = "APPOINTMENTS")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "uid")
public class Appointments extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    LocalDateTime scheduledAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    AppointmentsStatusEnum appointmentsStatus;

    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    List<Services> services;

    BigDecimal totalValue;

}
