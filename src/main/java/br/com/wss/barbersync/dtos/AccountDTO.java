package br.com.wss.barbersync.dtos;

import br.com.wss.barbersync.enums.Role;
import br.com.wss.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountDTO extends BaseDTO<String> {

    @Size(min=3, message = "Nome deve conter no mínimo 3 letras")
    @Pattern(
            regexp = "^[\\p{L} ]+$",
            message = "Nome deve conter apenas letras e espaços"
    )
    private String name;

    private String taxNumber;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    private String phone;

    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min=8, message = "Senha deve conter no mínimo 8 caracteres")
    private String password;
}
