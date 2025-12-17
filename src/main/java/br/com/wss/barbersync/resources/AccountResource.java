package br.com.wss.barbersync.resources;

import br.com.wss.barbersync.business.AccountBusiness;
import br.com.wss.barbersync.converters.AccountConverter;
import br.com.wss.barbersync.dtos.AccountDTO;
import br.com.wss.barbersync.entities.Account;
import br.com.wss.barbersync.enums.Role;
import br.com.wss.base.PageImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountResource {

    private final AccountBusiness accountBusiness;

    private final AccountConverter accountConverter;

    public static final String ACCOUNTS = "/accounts";
    public static final String CREATE_ACCOUNTS = ACCOUNTS + "/create";
    public static final String CREATE_ACCOUNTS_ROLES = CREATE_ACCOUNTS + "/roles";
    public static final String FIND_ALL = ACCOUNTS + "/all";
    public static final String FIND_PARAM = ACCOUNTS + "/params";

    @Operation(
            summary = "Criar conta",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Exemplo de cadastro",
                                    value = """
                                            {
                                              "name": "Willians Silva Santos",
                                              "taxNumber": "42718894075",
                                              "phone": "85999990000",
                                              "email": "willians@email.com",
                                              "password": "Senha@12345"
                                            }
                                    """
                            )
                    )
            )
    )
    @PostMapping(CREATE_ACCOUNTS)
    public ResponseEntity<Void> createAccount(@RequestBody @Valid final AccountDTO accountDTO){
        try {
            accountBusiness.insert(accountConverter.convertToEntity(accountDTO));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(
            summary = "Lista de contas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de contas",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AccountDTO.class),
                                    examples = @ExampleObject(
                                            name = "Exemplo de retorno",
                                            value = """
          [
            {
              "uid": "a1b2c3d4",
              "deleted": false,
              "createdAt": "2025-12-15T23:37:56.499",
              "createdByUid": "u123",
              "createdByName": "Admin",
              "updatedAt": "2025-12-15T23:37:56.499",
              "updatedByUid": "u123",
              "updatedByName": "Admin",
              "deletedAt": null,
              "deletedByUid": null,
              "deletedByName": null,
              "name": "Willians Silva Santos",
              "taxNumber": "42718894075",
              "phone": "85999990000",
              "email": "willians@email.com"
            }
          ]
          """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão")
            }
    )
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<AccountDTO>> getAll(){
        return ResponseEntity.ok().body(
                accountConverter.convertToDTOList(
                        accountBusiness.findAll()));
    }

    @GetMapping(FIND_PARAM)
    public ResponseEntity<PageImpl<AccountDTO>> findByParams(
            @RequestParam(required = false) final String uid,
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String taxNumber,
            @RequestParam(required = false) final String email,
            @RequestParam(required = false) final String phone,
            @RequestParam(required = false) final String createdByName,
            @RequestParam(required = false) final String updatedByName,
            @RequestParam(required = false) final Role role,
            @RequestParam(required = false) final Boolean active,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") final LocalDateTime createdStartAt,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") final LocalDateTime createdEndAt,
            @RequestParam(required = false, defaultValue = "0") final int page,
            @RequestParam(required = false, defaultValue = "50") final int size,
            @RequestParam(required = false, defaultValue = "name") final String sort,
            @RequestParam(required = false, defaultValue = "ASC") final Direction direction) {

        final Page<AccountDTO> resultPage = accountBusiness.findByParams(uid, name, taxNumber, email, phone, createdByName, updatedByName, role, active, createdStartAt, createdEndAt, PageRequest.of(page, size, Sort.by(direction, sort))).map(accountConverter::convertToDTO);

        if (!resultPage.hasContent())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(new PageImpl<>(resultPage));

    }

    @Operation(
            summary = "Criar conta com a definição do cargo, somente administrador pode realizar o cadastro",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Exemplo de cadastro",
                                    value = """
                                            {
                                              "name": "Willians Silva Santos",
                                              "taxNumber": "42718894075",
                                              "phone": "85999990000",
                                              "email": "willians@email.com",
                                              "password": "Senha@12345",
                                              "role": "ROLE_USER"
                                            }
                                    """
                            )
                    )
            )
    )
    @PostMapping(CREATE_ACCOUNTS_ROLES)
    public ResponseEntity<?> createAccountRole(@RequestBody final AccountDTO accountDTO){
        try {
            Account accountCreate = accountBusiness.insert(accountConverter.convertToEntity(accountDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(accountConverter.convertToDTO(accountCreate));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping(ACCOUNTS)
    public ResponseEntity<AccountDTO> getCurrentAccount(){
        AccountDTO dto = accountConverter.convertToDTO(accountBusiness.getCurrentAccount());
        return ResponseEntity.ok(dto);
    }
}
