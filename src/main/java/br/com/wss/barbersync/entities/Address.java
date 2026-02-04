package br.com.wss.barbersync.entities;

import br.com.wss.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
//@Table(schema = GenericUtils.SCHEMA)
@Table(name = "ADDRESS")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "uid")
public class Address extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    private String street;

    private String number;

    private String neighborhood;

    private String city;

    private String zipCode;

}
