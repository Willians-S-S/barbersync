package br.com.wss.barbersync.enums;

import br.com.wss.exception.BusinessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum RoleAccountEnum {

    ROLE_CLIENT("Cliente"),
    ROLE_ADM("Administrador"),
    ROLE_OWNER("Dono"),
    ROLE_EMPLOYEE("Empregado");


    private final String name;

    RoleAccountEnum(final String name) {
        this.name = name;
    }

    public static RoleAccountEnum of(final String value) {

        for (RoleAccountEnum roleAccountEnum : Arrays.asList(RoleAccountEnum.values())) {
            if (roleAccountEnum.getName().equals(value))
                return roleAccountEnum;
        }

        throw new BusinessException(HttpStatus.BAD_REQUEST,
                "Os valores válidos são: " + Arrays.stream(RoleAccountEnum.values()).map(RoleAccountEnum::getName)
                        .toList().toString());

    }

    public static Stream<Map<String, String>> getValues() {
        return Arrays.stream(RoleAccountEnum.values()).map(en -> Map.of(en.name(), en.getName()));
    }
}
