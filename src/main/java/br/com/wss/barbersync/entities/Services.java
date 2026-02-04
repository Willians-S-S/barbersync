package br.com.wss.barbersync.entities;

import br.com.wss.barbersync.enums.RoleAccountEnum;
import br.com.wss.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
//@Table(schema = GenericUtils.SCHEMA)
@Table(name = "SERVICES")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "uid")
public class Services extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private String category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "EMPLOYEE_SERVICE",
            joinColumns = @JoinColumn(name = "employees_uid"),
            inverseJoinColumns = @JoinColumn(name = "services_uid"))
    private List<Employee> employees;
}
