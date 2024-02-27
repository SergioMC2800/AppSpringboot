package org.jrotero.controller.security;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Esta clase de UserDto sirve para poder usarlo de intermediario entre las consultas y los usuarios finales, para que así no haya problemas
 * a la hora de realizar consultas en el repositorio donde se puedan sufrir filtraciones
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String password;
	private Set<String> roles;

}
