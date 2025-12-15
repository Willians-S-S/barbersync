package br.com.wss.barbersync.repositories;

import br.com.wss.barbersync.entities.Account;
import br.com.wss.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account, String> {

    Optional<Account> findByEmail(String email);
}
