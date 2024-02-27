package org.jrotero.service;

import java.util.List;
import java.util.Optional;

import org.jrotero.model.TaskEntity;
import org.jrotero.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements ITaskService{

	/*
	 * Genero el constructor con el repositorio para la inyección de dependencias
	 */
	private ITaskRepository repository;
	
	public TaskServiceImpl(ITaskRepository repository) {
		this.repository = repository;
	}

	/*
	 * Genero la implementación del metodo findAll
	 */
	@Override
	public List<TaskEntity> findAll() {
		return this.repository.findAll();
	}

	/*
	 * Genero la implementación del metodo findById
	 */
	@Override
	public Optional<TaskEntity> findById(Long id) {
		return this.repository.findById(id);
	}

	/*
	 * Genero la implementación del metodo create
	 */
	@Override
	public TaskEntity create(TaskEntity task) {
		var taskResponse = new TaskEntity();
		return repository.save(task);
	}
	
	/*
	 * Genero la implementación del metodo deleteById
	 */
	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	/*
	 * Genero la implementación del metodo update
	 */
	@Override
	public TaskEntity update(TaskEntity task, Long id) throws Exception {
		/*
		 * Con el if compruebo que en el repositorio existe un task con ese id
		 */
		if(repository.findById(id) != null) {
			/*
			 * Si existe guardo la task actualizada y sustituyo la anterior
			 */
			var response = task;
			response.setId(id);	
			return repository.save(response);
		}
		return null;
	}

}
