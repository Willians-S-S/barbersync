package br.com.wss.barbersync.enums;

import br.com.wss.exception.BusinessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum Role {

    ROLE_USER("Usuário"),
    ROLE_ADM("Administrador");


    private String name;

    Role(final String name) {
        this.name = name;
    }

    public static Role of(final String value) {

        for (Role role: Arrays.asList(Role.values())) {
            if (role.getName().equals(value))
                return role;
        }

        throw new BusinessException(HttpStatus.BAD_REQUEST,
                "Os valores válidos são: " + Arrays.stream(Role.values()).map(Role::getName)
                        .toList().toString());

    }

    public static Stream<Map<String, String>> getValues() {
        return Arrays.stream(Role.values()).map(en -> Map.of(en.name(), en.getName()));
    }
}
