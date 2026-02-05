package br.com.wss.barbersync.repositories;

import br.com.wss.barbersync.entities.Appointments;
import br.com.wss.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentsRepository extends BaseRepository<Appointments, String> {

}
