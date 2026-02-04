package br.com.wss.barbersync.entities;

import br.com.wss.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
//@Table(schema = GenericUtils.SCHEMA)
@Table(name = "OWNER")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "uid")
public class Owner extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "OWNER_BARBERSHOP",
        joinColumns = @JoinColumn(name = "owner_uid"),
        inverseJoinColumns = @JoinColumn(name = "barbershop_uid"))
    private List<Barbershop> barbershops;
}
