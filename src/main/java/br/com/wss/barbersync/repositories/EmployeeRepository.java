package br.com.wss.barbersync.repositories;

import br.com.wss.barbersync.entities.Employee;
import br.com.wss.barbersync.entities.Owner;
import br.com.wss.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, String> {

}
