package org.jrotero.repository.security;

import org.jrotero.model.security.RolEnt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends CrudRepository<RolEnt, Long>{

}
