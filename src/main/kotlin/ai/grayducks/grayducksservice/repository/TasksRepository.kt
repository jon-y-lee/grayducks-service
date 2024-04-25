package ai.grayducks.grayducksservice.repository

import ai.grayducks.grayducksservice.repository.entities.TaskListEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TasksRepository : JpaRepository<TaskListEntity, Long> {

    fun findByUserId(id: String): List<TaskListEntity>
}