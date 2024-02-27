package org.jrotero.service;

import java.util.List;
import java.util.Optional;

import org.jrotero.model.TaskEntity;

public interface ITaskService {

	//Get
	List<TaskEntity> findAll();
	
	Optional<TaskEntity> findById(Long id);
	
	//Create - Update
	public TaskEntity create(TaskEntity task) throws Exception;
	
	public TaskEntity update(TaskEntity task, Long id) throws Exception;
;
	
	//Delete
	void deleteById(Long id);
}
