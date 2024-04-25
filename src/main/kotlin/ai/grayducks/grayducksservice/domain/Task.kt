package ai.grayducks.grayducksservice.domain

import ai.grayducks.grayducksservice.repository.entities.TaskListEntity
import ai.grayducks.grayducksservice.repository.entities.UserEntity

data class TaskResponse(
    val items: List<Task>?
) {}

data class Task(
    val id: String?,
    val name: String?,
    val taskListId: String?,
    val userId: String?
) {}

data class TaskListCreateResponse(
    val id: String,
    val title: String
) {}

data class TaskList(
    val id: String?,
    val title: String?,
) {}

fun TaskListCreateResponse.mapToTaskList(): TaskList {
    return TaskList(id, title)
}
