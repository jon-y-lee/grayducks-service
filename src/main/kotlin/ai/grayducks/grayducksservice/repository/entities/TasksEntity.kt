package ai.grayducks.grayducksservice.repository.entities

import ai.grayducks.grayducksservice.domain.Profile
import ai.grayducks.grayducksservice.domain.TaskList
import jakarta.persistence.*

@Entity
class TaskListEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var userId: String,

    @Column(nullable = false)
    var taskListId: String,
) {
    constructor() : this(null, userId = "", taskListId = "") {}
}

fun TaskListEntity.mapToTaskList(): TaskList {
    return TaskList(taskListId, "")
}
