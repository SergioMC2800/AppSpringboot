package org.jrotero.model.security;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEnt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String username;
	private String name;
	private String password;

	/*
	 * Con @ManyToMany declaramos que la relación entre la clase Usuario y la de Rol
	 * es de muchos a muchos, por lo que un usuario puede tener varios roles y cada
	 * rol puede estar asociado a varios users
	 * 
	 * Con @JoinTable nos metemos en otra tabla de datos para conseguir información
	 * de ella y en este caso la usamos para obtener los roles y asociarlos a cada
	 * usuario
	 */

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = RolEnt.class, cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RolEnt> roles;

}
