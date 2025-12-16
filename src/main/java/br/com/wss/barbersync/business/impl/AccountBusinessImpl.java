package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.AccountBusiness;
import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.enums.Role;
import br.com.wss.barbersync.repositories.AccountRepository;
import br.com.wss.barbersync.repositories.projections.AccountProjection;
import br.com.wss.base.AbstractBusinessImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
//@PropertySource("classpath:messageSources.properties")
@Slf4j
@AllArgsConstructor
public class AccountBusinessImpl extends AbstractBusinessImpl<Account, String> implements AccountBusiness {

    @Getter
    private final AccountRepository repository;

    private final PasswordEncoder passwordEncoder;

    public Optional<Account> findByEmail(String email){
        return getRepository().findByEmail(email);
    }

    @Override
    public Account insert(final Account entity){
        entity.setUid(null);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        entity.setRole(Role.ROLE_USER);

        return super.insert(entity);
    }

    public List<Account> findAll(){
        return getRepository().findAll();
    }

    public Page<Account> findByParams(final String uid, final String name, final String taxNumber,
                                         final String email, final String phone, final String createdByName, final String updatedByName,
                                         final Role role, final Boolean active, final LocalDateTime createdStartAt, final LocalDateTime createdEndAt,
                                         final Pageable pageable){
        return getRepository().findByParams(uid, name, taxNumber, email, phone, createdByName, updatedByName, role, active, createdStartAt, createdEndAt, pageable);
    }
}
