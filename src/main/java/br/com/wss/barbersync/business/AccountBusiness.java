package br.com.wss.barbersync.business;

import br.com.wss.barbersync.entities.Account;
import br.com.wss.base.BaseBusiness;
import org.springframework.stereotype.Controller;

@Controller
public interface AccountBusiness extends BaseBusiness<Account, String> {
}
