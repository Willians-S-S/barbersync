package br.com.wss.barbersync.entities;

import br.com.wss.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
//@Table(schema = GenericUtils.SCHEMA)
@Table(name = "ACCOUNT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "uid")
public class Barbershop extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    private String name;

    @Column(unique = true, nullable = false)
    private String taxNumber;

    private LocalTime opening_hours;
    private LocalTime closing_hours;

    private String contact; //Todo: talez trocar por uma classe de contato, com whatsapp, intagram ...

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Owner owner;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employee;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "OWNER_BARBERSHOP",
            joinColumns = @JoinColumn(name = "barbershop_uid"),
            inverseJoinColumns = @JoinColumn(name = "owner_uid"))
    private List<Owner> owners;
}
