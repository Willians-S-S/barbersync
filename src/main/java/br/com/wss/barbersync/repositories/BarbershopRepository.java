package br.com.wss.barbersync.repositories;

import br.com.wss.barbersync.entities.Barbershop;
import br.com.wss.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbershopRepository extends BaseRepository<Barbershop, String> {

}
