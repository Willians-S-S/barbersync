package br.com.wss.barbersync.converters;

import br.com.wss.barbersync.dtos.AccountDTO;
import br.com.wss.barbersync.entities.Account;
import br.com.wss.base.BaseConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountConverter extends BaseConverter<Account, AccountDTO> {

    @Override
    public AccountDTO convertToDTO(final Account entity) {
        final AccountDTO dto = new AccountDTO();

        BeanUtils.copyProperties(entity, dto);

        dto.setUid(entity.getUid());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setDeleted(entity.getDeleted());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    @Override
    public Account convertToEntity(final AccountDTO dto) {
        final Account entity = new Account();

        BeanUtils.copyProperties(dto, entity);

        entity.setDeletedAt(dto.getDeletedAt());
        entity.setDeleted(dto.getDeleted());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }

    public Account convertToEntityInsert(final AccountDTO dto){
        final Account entity = new Account();
        BeanUtils.copyProperties(dto, entity, "uid");

        return entity;
    }
}
