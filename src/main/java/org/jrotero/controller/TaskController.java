package org.jrotero.controller;

import java.util.List;

import org.jrotero.model.TaskEntity;
import org.jrotero.service.ITaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * Con @PreAuthorize marcamos los roles que permiten realizar estas consultas
 * 
 * Con @GetMapping, @PostMapping, @DeleteMapping y @PutMapping marcamos de que tipo va a ser la consulta que vamos a realizar
 */

@RestController
public class TaskController {

	private ITaskService service;

	/*
	 * Con este constructor realizamos la inyección de la interfaz para poder
	 * acceder a todas las consultas
	 */

	public TaskController(ITaskService service) {
		this.service = service;
	}

	/*
	 * Con este metodo, mediante una petición "Get", si tenemos los roles de Admin o
	 * de User, se nos muestran todas las tasks que tenemos almacenadas en nuestra
	 * base de datos
	 */

	@GetMapping("/get-all")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<List<TaskEntity>> findAll() {
		List<TaskEntity> tasks = this.service.findAll();
		if (tasks.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(tasks);
	}

	/*
	 * Con este método, mediante una petición "Get", si tenemos los roles de Admin o
	 * de User, además de añadir el id que buscas en la ruta de la consulta, nos
	 * buscará la Task con esa id y nos la mostrará por pantalla
	 */

	@GetMapping("/get-id/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<TaskEntity> findById(@PathVariable("id")Long id) {
		TaskEntity task = service.findById(id).orElseGet(null);
		if (task == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(task);
		}
	}

	/*
	 * Con este método, mediante una petición "Post", si tenemos el rol de Admin, al
	 * añadir en el body de la petición todas las partes correspondientes de una
	 * task, nos permite crear una task nueva y añadirla a la base de datos
	 */

	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TaskEntity> create(@RequestBody TaskEntity task) throws Exception {
		service.create(task);
		return ResponseEntity.ok(task);
	}

	/*
	 * Con este método, mediante una petición "Put", si tenemos los roles de Admin o
	 * de User, al añadir en el body de la petición todas las partes
	 * correspondientes de una task, y al añadir el id de la task que queremos
	 * modificar, nos permite actualizar la task correspondiente con los datos que
	 * queramos introducir
	 */

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<TaskEntity> update(@RequestBody TaskEntity task, @PathVariable("id") Long id)
			throws Exception {
		service.update(task, id);
		return ResponseEntity.ok(task);
	}

	/*
	 * Con este método, mediante una petición "Put", si tenemos el rol de Admin, al
	 * añadir el id de la task que queremos eliminar, nos permite borrar la task
	 * correspondiente de la base de datos
	 */

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TaskEntity> deleteById(@PathVariable("id") Long id) {
		service.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
