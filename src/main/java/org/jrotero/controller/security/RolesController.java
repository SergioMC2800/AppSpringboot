package org.jrotero.controller.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RolesController {
	
	@GetMapping("/access-admin")
	//@PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String accessAdmin() {
		return "Hola eres el administrador";
	}
	
	@GetMapping("/access-user")
	@PreAuthorize("hasRole('USER')")
	public String accessUser() {
		return "Hola eres el usuario";
	}
	
	@GetMapping("/access-invited")
	@PreAuthorize("hasRole('INVITED')")
	public String accessInvited() {
		return "Hola eres el invitado";
	}
}
