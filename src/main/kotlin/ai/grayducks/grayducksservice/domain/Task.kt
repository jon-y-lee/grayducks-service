package ai.grayducks.grayducksservice.domain

data class TaskResponse(
    val items: List<Task>?
    ) {}

data class Task(
    val id: String?,
    val title: String?,
    val updated: String?){}