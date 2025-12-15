package br.com.wss.barbersync.resources;

import br.com.wss.barbersync.business.AccountBusiness;
import br.com.wss.barbersync.converters.AccountConverter;
import br.com.wss.barbersync.dtos.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountResource {

    private final AccountBusiness accountBusiness;

    private final AccountConverter accountConverter;

    public static final String ACCOUNTS = "/accounts";

    @PostMapping(ACCOUNTS)
    public ResponseEntity<Void> createAccount(@RequestBody final AccountDTO accountDTO){
        accountBusiness.insert(accountConverter.convertToEntity(accountDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(ACCOUNTS)
    public ResponseEntity<List<AccountDTO>> getAll(){
        return ResponseEntity.ok().body(
                accountConverter.convertToDTOList(
                        accountBusiness.findAll()));
    }
}
