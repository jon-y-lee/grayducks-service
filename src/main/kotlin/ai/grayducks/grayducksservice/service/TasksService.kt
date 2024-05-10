package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.config.Constants.Companion.GOOGLE_TASKS_URI
import ai.grayducks.grayducksservice.config.Constants.Companion.GOOGLE_TASK_LIST_URI
import ai.grayducks.grayducksservice.domain.*
import ai.grayducks.grayducksservice.repository.TasksRepository
import ai.grayducks.grayducksservice.repository.entities.TaskListEntity
import ai.grayducks.grayducksservice.repository.entities.mapToTaskList
import ai.grayducks.grayducksservice.service.interfaces.HttpInterface
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate


@Service
class TasksService(
    @Autowired val restTemplate: RestTemplate,
    @Autowired val taskRepository: TasksRepository
) : HttpInterface {

    private val log = KotlinLogging.logger {}

    fun getTaskLists(token: String, user: UserInfo?): MutableList<TaskList> {
        log.info("Tasks   :" + user)

        val taskListsIds = taskRepository.findByUserId(user!!.id)

        val httpEntity = constructHeader(token, null);
        log.info("break 1 " + " " + taskListsIds.size)

        var taskLists = mutableListOf<TaskList>();

        for (taskList in taskListsIds) {
            log.info("break 2 ")
            val events =
                restTemplate.exchange(
                    GOOGLE_TASK_LIST_URI + "/" + taskList.taskListId,
                    HttpMethod.GET,
                    httpEntity,
                    TaskList::class.java
                )
            println("Tasks:" + events.body.toString())
            var taskListProjection = events.body

            val taskListEntity = taskRepository.findByTaskListId(taskList.taskListId);
            taskListProjection!!.assignedProfileId = taskListEntity.assignedProfileId;

            taskLists.add(taskListProjection);
        }

        return taskLists
    }

    fun createTaskList(token: String, userProfile: UserInfo?, taskList: TaskList?): TaskList? {
        val body: HashMap<String, String> = HashMap()
        if (taskList?.id != null) {
            body.put("id", taskList.id)
        }
        if (taskList?.title != null) {
            body.put("title", taskList.title)
        }

        val httpEntity = constructHeader(token, body);
        var url = GOOGLE_TASK_LIST_URI;
        var method = HttpMethod.POST;
        if (taskList?.id != null) {
            url = GOOGLE_TASK_LIST_URI + "/" + taskList.id
            method = HttpMethod.PUT
        }

        val events =
            restTemplate.exchange(
                url,
                method,
                httpEntity,
                TaskListCreateResponse::class.java
            )
        val newTaskList = events.body
        println("Tasks:" + events.body.toString())

        taskRepository.save(TaskListEntity(null, userProfile!!.id, newTaskList!!.id, taskList?.assignedProfileId))

        return newTaskList.mapToTaskList()
    }

    fun getTasksByTaskListId(token: String, userProfile: UserInfo?, taskListId: String): TaskResponse? {
        val httpEntity = constructHeader(token, null);

        val events =
            restTemplate.exchange(
                GOOGLE_TASKS_URI + "/" + taskListId + "/tasks",
                HttpMethod.GET,
                httpEntity,
                TaskResponse::class.java
            )

        for (task in events.body?.items!!) {
            task.taskListId = taskListId;
        }

        return events.body
    }

    fun getTask(token: String, userProfile: UserInfo?, taskListId: String, taskId: String): Task? {
        val httpEntity = constructHeader(token, null);

        val events =
            restTemplate.exchange(
                GOOGLE_TASKS_URI + "/" + taskListId + "/tasks/" + taskId,
                HttpMethod.GET,
                httpEntity,
                Task::class.java
            )
        println("GET TASSK : " + events.body.toString())
        events.body
        return events.body
    }

    fun updateTask(token: String, userProfile: UserInfo?, taskListId: String, taskId: String, task: Task): Any? {
        val httpEntity = constructHeader(token, task);

        val events =
            restTemplate.exchange(
                GOOGLE_TASKS_URI + "/" + taskListId + "/tasks/" + taskId,
                HttpMethod.PUT,
                httpEntity,
                TaskResponse::class.java
            )
        println("Tasks:" + events.body.toString())
        events.body
        return events.body
    }

    fun createTask(token: String, userProfile: UserInfo?, taskListId: String, task: Task): Any? {
        val httpEntity = constructHeader(token, task);

        val events =
            restTemplate.exchange(
                GOOGLE_TASKS_URI + "/" + taskListId + "/tasks",
                HttpMethod.POST,
                httpEntity,
                TaskResponse::class.java
            )
        println("Created:" + events.body.toString())
        events.body

        return events.body

    }

    fun deleteTaskList(token: String, userProfile: UserInfo?, id: String): Any? {

        println("delete id:" + id)
        val httpEntity = constructHeader(token, null);
        var url = GOOGLE_TASK_LIST_URI + "/" + id;
        var method = HttpMethod.DELETE;

        println(url)
        restTemplate.exchange(
            url,
            method,
            httpEntity,
            String::class.java
        )

        val tsk = taskRepository.findByTaskListId(id)
        val deletedTaskList: Unit = taskRepository.delete(tsk)
        return deletedTaskList;
    }

    fun deleteTask(token: String, userProfile: UserInfo?, taskListId: String, taskId: String): Any? {
        val httpEntity = constructHeader(token, null);

        val events =
            restTemplate.exchange(
                GOOGLE_TASKS_URI + "/" + taskListId + "/tasks/" + taskId,
                HttpMethod.DELETE,
                httpEntity,
                TaskResponse::class.java
            )
        events.body
        return events.body
    }

}