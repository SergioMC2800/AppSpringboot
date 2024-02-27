package org.jrotero.repository;

import org.jrotero.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends JpaRepository<TaskEntity, Long>{

}
