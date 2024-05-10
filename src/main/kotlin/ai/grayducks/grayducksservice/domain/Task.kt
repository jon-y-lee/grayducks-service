package ai.grayducks.grayducksservice.domain

import ai.grayducks.grayducksservice.repository.entities.TaskListEntity
import ai.grayducks.grayducksservice.repository.entities.UserEntity

data class TaskResponse(
    val items: List<Task>?
) {}

data class Task(
    var taskListId: String?,
    val id: String?,
    val title: String?,
    val notes: String?,
    var status: String?,
    val completed: String?,
    val deleted: String?,
    val hidden: Boolean?,
    val userId: String?
) {}

data class TaskListCreateResponse(
    val id: String,
    val title: String,
    val assignedProfileId: String?,
    ) {}

data class TaskList(
    val id: String?,
    val title: String?,
    var assignedProfileId: String?,
//    var tasks: List<Task>?,
) {}

fun TaskListCreateResponse.mapToTaskList(): TaskList {
    return TaskList(id, title, assignedProfileId)
}
