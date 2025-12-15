package br.com.wss.barbersync.business;

import br.com.wss.barbersync.entities.Account;
import br.com.wss.base.BaseBusiness;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

//@Controller
public interface AccountBusiness extends BaseBusiness<Account, String> {

    Optional<Account> findByEmail(String email);
    List<Account> findAll();
}
