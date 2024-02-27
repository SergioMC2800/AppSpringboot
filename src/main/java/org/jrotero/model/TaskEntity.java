package org.jrotero.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Aquí se crea la clase de Task con todos los parametros
 * 
 * Con la anotación @Entity definimos que en nuestra base de datos una tabla con estos parámetros
 * 
 * Con @Table definimos el nombre con el que vamos a llamar a la tabla dentro de la base de datos
 * 
 * Con @Builder definimos la clase de lombok lo que nos permite generar los constructores o poder usar los metodos builder para generar entidades
 * 
 * Con @AllArgsConstructor y @NoArgsConstructor se generan los constructores con todos y con ningún parámetro
 * 
 * Con @Data se generan los metodos Getters y Setters además del Tostring para estas clases
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class TaskEntity {

	/*
	 * Con @id definimos que ese parametro va a ser la Primary Key de la clase
	 * 
	 * Con @GeneratedValue hacemos que se vaya generando de manera automática el id
	 * avanzando cada vez un número
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String descript;

	/*
	 * Con @JsonFormat defino el formato en el que se va a introducir la fecha
	 */

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dueDate;

	private String tag;

	private Boolean completed;

}
