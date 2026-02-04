package br.com.wss.barbersync.enums;

import br.com.wss.exception.BusinessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum EmployeeEnum {

    BABER("Barbeiro"),
    RECEPCIONIST("Recepcionista");

    private final String name;

    EmployeeEnum(final String name) {
        this.name = name;
    }

    public static EmployeeEnum of(final String value) {

        for (EmployeeEnum roleAccountEnum : Arrays.asList(EmployeeEnum.values())) {
            if (roleAccountEnum.getName().equals(value))
                return roleAccountEnum;
        }

        throw new BusinessException(HttpStatus.BAD_REQUEST,
                "Os valores válidos são: " + Arrays.stream(EmployeeEnum.values()).map(EmployeeEnum::getName)
                        .toList().toString());

    }

    public static Stream<Map<String, String>> getValues() {
        return Arrays.stream(EmployeeEnum.values()).map(en -> Map.of(en.name(), en.getName()));
    }
}
