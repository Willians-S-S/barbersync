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
@Table(name = "CLIENT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "uid")
public class Client extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(unique = true, nullable = false)
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    List<Appointments> appointments;
}
