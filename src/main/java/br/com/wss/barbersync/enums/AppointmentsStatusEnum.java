package br.com.wss.barbersync.enums;

import br.com.wss.exception.BusinessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum AppointmentsStatusEnum {

    SCHEDULED("Agendado"),
    CANCELED("Cancelado"),
    UNATTENDED("Não Atendido"),
    COMPLETED("Completado");

    private final String name;

    AppointmentsStatusEnum(final String name) {
        this.name = name;
    }

    public static AppointmentsStatusEnum of(final String value) {

        for (AppointmentsStatusEnum roleAccountEnum : Arrays.asList(AppointmentsStatusEnum.values())) {
            if (roleAccountEnum.getName().equals(value))
                return roleAccountEnum;
        }

        throw new BusinessException(HttpStatus.BAD_REQUEST,
                "Os valores válidos são: " + Arrays.stream(AppointmentsStatusEnum.values()).map(AppointmentsStatusEnum::getName)
                        .toList().toString());

    }

    public static Stream<Map<String, String>> getValues() {
        return Arrays.stream(AppointmentsStatusEnum.values()).map(en -> Map.of(en.name(), en.getName()));
    }
}
