package br.com.wss.barbersync.entities;

import br.com.wss.barbersync.enums.Role;
import br.com.wss.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
//@Table(schema = GenericUtils.SCHEMA)
@Table(name = "ACCOUNT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "uid")
public class Account extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    private String name;

    @Column(unique = true, nullable = false)
    private String taxNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
}
