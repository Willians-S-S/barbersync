package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.AccountBusiness;
import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.enums.Role;
import br.com.wss.barbersync.repositories.AccountRepository;
import br.com.wss.base.AbstractBusinessImpl;
import br.com.wss.base.TransactionType;
import br.com.wss.exception.BusinessException;
import br.com.wss.filters.JwtToken.UserTokenDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
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

        validate(entity, TransactionType.INSERT);

        entity.setUid(null);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        UserTokenDetails userTokenDetails = super.getUserDetails();

        Role existingRole = Optional.ofNullable(userTokenDetails.getAccount())
                .map(Account::getRole)
                .orElse(null);

        if (existingRole == null || Role.ROLE_USER.equals(existingRole))
            entity.setRole(Role.ROLE_USER);

        entity.setDeleted(false);

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

    @Override
    protected void validate(final Account entity, final TransactionType transactionType) {
        getRepository().findByEmail(entity.getEmail()).ifPresent(acc -> {
            throw new BusinessException(HttpStatus.CONFLICT, "Já existe uma conta cadastrada com este e-mail.");
        });

        getRepository().findByTaxNumber(entity.getTaxNumber()).ifPresent(acc -> {
            throw new BusinessException(HttpStatus.CONFLICT, "Já existe uma conta cadastrada com este CPF/CNPJ.");
        });

        getRepository().findByPhone(entity.getPhone()).ifPresent(acc -> {
            throw new BusinessException(HttpStatus.CONFLICT, "Já existe uma conta cadastrada com este número.");
        });
    }

    public Account getCurrentAccount(){
        Account loggedUser = super.getUserDetails().getAccount();

        return Optional.ofNullable(loggedUser).orElseThrow(() -> new BusinessException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado."));
    }

    @Override
    public Account update(final Account entity) {
        Account loggedAccount = super.getUserDetails().getAccount();

        loggedAccount.setName(entity.getName());

        if(!loggedAccount.getEmail().equals(entity.getEmail())){
            getRepository().findByEmail(entity.getEmail())
                    .ifPresent(account -> {
                        throw new BusinessException(HttpStatus.CONFLICT, "Já existe uma conta cadastrada com este e-mail.");
                    });
            loggedAccount.setEmail(entity.getEmail());
        }

        if(!loggedAccount.getPhone().equals(entity.getPhone())){
            getRepository().findByPhone(entity.getPhone())
                    .ifPresent(account -> {
                        throw new BusinessException(HttpStatus.CONFLICT, "Já existe uma conta cadastrada com este número.");
                    });
            loggedAccount.setPhone(entity.getPhone());
        }

        if(!loggedAccount.getTaxNumber().equals(entity.getTaxNumber())){
            getRepository().findByTaxNumber(entity.getTaxNumber())
                    .ifPresent(account -> {
                        throw new BusinessException(HttpStatus.CONFLICT, "Já existe uma conta cadastrada com este número.");
                    });
            loggedAccount.setTaxNumber(entity.getTaxNumber());
        }

        loggedAccount.setUpdatedAt(LocalDateTime.now());
        loggedAccount.setUpdatedByName(entity.getName());
        loggedAccount.setUpdatedByUid(loggedAccount.getUid());

        return getRepository().save(loggedAccount);
    }
}
