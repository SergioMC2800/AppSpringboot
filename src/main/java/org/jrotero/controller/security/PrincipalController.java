package org.jrotero.controller.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.jrotero.model.security.ERoles;
import org.jrotero.model.security.RolEnt;
import org.jrotero.model.security.UserEnt;
import org.jrotero.repository.security.IUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class PrincipalController {

	private PasswordEncoder passwordEncoder;

	private IUserRepository userRepository;
	
	/*
	 * Con este constructor realizamos la inyección de la interfaz y del repositorio para poder acceder a todas las consultas
	 */
	public PrincipalController(PasswordEncoder passwordEncoder, IUserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}
	
	/*
	 * Con este método, mediante una petición "Get", se genera un primer usuario con rol de Admin para poder empezar a gestionar la aplicación
	 */

	@GetMapping("/create-first-user")
	public String createFirstUser() {
		UserEnt firstUser = UserEnt.builder()
				.email("primerUser@gmail.com")
				.username("prueba123")
				.name("Prueba")
				.password(passwordEncoder.encode("1234"))
				.roles(Set.of(RolEnt.builder()
						.roles(ERoles.valueOf(ERoles.ADMIN.name()))
						.build()))
				.build();
		
		userRepository.save(firstUser);
		return "Usuario inicial creado con éxito";

	}

	/*
	 * Con este método, mediante una petición "Post", si tenemos el rol de Admin, al añadir en el body de la petición todas las partes
	 * correspondientes de un usuario, nos permite crear un usuario nuevo y añadirlp a la base de datos
	 */
	
	@PostMapping("/createUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDto) {

		Set<RolEnt> roles = userDto.getRoles().stream()
				.map(role -> RolEnt.builder().roles(ERoles.valueOf(role)).build()).collect(Collectors.toSet());

		UserEnt userEntity = UserEnt.builder()
				.username(userDto.getUsername())
				.name(userDto.getName())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.email(userDto.getEmail())
				.roles(roles)
				.build();

		userRepository.save(userEntity);

		return ResponseEntity.ok(userEntity);
	}
	
	/*
	 * Con este método, mediante una petición "Delete", si tenemos el rol de Admin, además de añadir la id en la ruta para realizar la consulta
	 * elminamos el user que tenga esa id
	 */

	@DeleteMapping("/deleteUser/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteUser(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
		return "Se ha borrado el user con id: " + id;
	}

	/*
	 * Con este metodo, mediante una petición "Get", si tenemos los roles de Admin o de User, se nos muestran todos los users que tenemos
	 * almacenados en nuestra base de datos
	 */
	
	@GetMapping("/get-all-users")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Iterable<UserEnt> getAllUsers(){
		return userRepository.findAll();	
	}
	
	/* 
	 * Con este método, mediante una petición "Get", si tenemos los roles de Admin o de User, además de añadir el username que buscas en la ruta 
	 * de la consulta, nos buscará el usuario con ese username y nos lo mostrará por pantalla 
	 */
	
	@GetMapping("/find-user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public UserEnt getUser(@PathVariable String username) {
		return userRepository.findByUsername(username).orElseGet(null);
	}

}
