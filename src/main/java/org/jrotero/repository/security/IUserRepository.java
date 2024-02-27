package org.jrotero.repository.security;

import java.util.Optional;

import org.jrotero.model.security.UserEnt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<UserEnt, Long>{

	Optional<UserEnt> findByUsername(String username);
	
}
