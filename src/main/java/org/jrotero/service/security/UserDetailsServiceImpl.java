package org.jrotero.service.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jrotero.model.security.UserEnt;
import org.jrotero.repository.security.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private IUserRepository repository;
	
	public UserDetailsServiceImpl(IUserRepository repository) {
		this.repository = repository;
	}

	/*
	 * Implemento el metodo para buscar un usuario
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*
		 * Aquí buscamos al usuario con el username y si no lo encuentra lanza una excepción
		 */
		UserEnt userEntity = repository.findByUsername(username).orElseThrow(() -> 
		new UsernameNotFoundException("Usuario ".concat(username).concat(" no existe")));
		
		Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRoles().name())))
				.collect(Collectors.toSet());
		
		return new User(userEntity.getUsername(),
				userEntity.getPassword(),
				true,
				true,
				true,
				true,
				authorities);
	}

}
