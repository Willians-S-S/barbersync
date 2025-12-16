package br.com.wss.barbersync.resources;

import br.com.wss.barbersync.business.AccountBusiness;
import br.com.wss.barbersync.converters.AccountConverter;
import br.com.wss.barbersync.dtos.AccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(
            summary = "Criar conta",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Exemplo de cadastro",
                                    value = """
                                        [
                                            {
                                              "name": "Willians Silva Santos",
                                              "taxNumber": "42718894075",
                                              "phone": "85999990000",
                                              "email": "willians@email.com",
                                              "password": "Senha@12345"
                                            }
                                        ]
                                    """
                            )
                    )
            )
    )
    @PostMapping(ACCOUNTS)
    public ResponseEntity<Void> createAccount(@RequestBody @Valid final AccountDTO accountDTO){
        accountBusiness.insert(accountConverter.convertToEntity(accountDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    @GetMapping(ACCOUNTS)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<AccountDTO>> getAll(){
        return ResponseEntity.ok().body(
                accountConverter.convertToDTOList(
                        accountBusiness.findAll()));
    }
}
