package br.com.wss.barbersync.business.impl;

import br.com.wss.barbersync.business.OwnerBusiness;
import br.com.wss.barbersync.entities.Barbershop;
import br.com.wss.barbersync.entities.Owner;
import br.com.wss.barbersync.repositories.OwnerRepository;
import br.com.wss.base.AbstractBusinessImpl;
import br.com.wss.base.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@Slf4j
@AllArgsConstructor
public class OwnerBusinessImpl extends AbstractBusinessImpl<Owner, String> implements OwnerBusiness {

    @Getter
    private final OwnerRepository repository;

    @Override
    public Owner insert(Owner entity) {

        entity.setUid(null);
        entity = setDependencies(entity, TransactionType.INSERT);

        return super.insert(entity);
    }

    @Override
    protected Owner setDependencies(Owner entity, TransactionType transactionType) {
        if (!entity.getBarbershops().isEmpty()) {
            List<Barbershop> barbershops = entity.getBarbershops()
                    .stream()
                    .filter(barbershop -> findDependencyByUid(barbershop.getUid()).isPresent()).toList();
            entity.setBarbershops(barbershops);
        }
        return entity;
    }
}
