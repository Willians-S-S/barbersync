package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.AccountBusiness;
import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.enums.Role;
import br.com.wss.barbersync.repositories.AccountRepository;
import br.com.wss.base.AbstractBusinessImpl;
import br.com.wss.base.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
}
