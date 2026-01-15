package br.com.wss.barbersync.business;

import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.enums.Role;
import br.com.wss.base.BaseBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Controller
public interface AccountBusiness extends BaseBusiness<Account, String> {

    Optional<Account> findByEmail(String email);
    List<Account> findAll();

    Page<Account> findByParams(final String uid, final String name, final String taxNumber, final String email, final String phone, final String createdByName, final String updatedByName,
                                         final Role role, final Boolean active, final LocalDateTime createdStartAt, final LocalDateTime createdEndAt,
                                         final Pageable pageable);

    Account getCurrentAccount();

    void delete();
}
