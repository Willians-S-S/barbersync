package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.AccountBusiness;
import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.repositories.AccountRepository;
import br.com.wss.base.AbstractBusinessImpl;
import br.com.wss.base.BaseRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@PropertySource("classpath:messageSources.properties")
@Slf4j
@AllArgsConstructor
public class AccountBusinessImpl extends AbstractBusinessImpl<Account, String> implements AccountBusiness {

    @Getter
    private final AccountRepository repository;
}
