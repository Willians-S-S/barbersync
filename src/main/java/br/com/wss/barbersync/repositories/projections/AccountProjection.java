package br.com.wss.barbersync.repositories.projections;

import br.com.wss.barbersync.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface AccountProjection {

    String getUid();

    LocalDateTime getCreatedAt();

    String getCreatedByName();

    LocalDateTime getUpdatedAt();

    String getUpdatedByName();

    String getName();

    String getTaxNumber();

    Role getRole();

    String getPhone();

    String getEmail();

}
