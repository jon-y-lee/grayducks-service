package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.config.Constants.Companion.GOOGLE_TASKS_URI
import ai.grayducks.grayducksservice.domain.TaskList
import ai.grayducks.grayducksservice.domain.TaskListCreateResponse
import ai.grayducks.grayducksservice.domain.UserInfo
import ai.grayducks.grayducksservice.domain.mapToTaskList
import ai.grayducks.grayducksservice.repository.TasksRepository
import ai.grayducks.grayducksservice.repository.entities.TaskListEntity
import ai.grayducks.grayducksservice.service.interfaces.HttpInterface
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
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

        log.info("Tasks  ids  :" + taskListsIds[0].taskListId)

        val httpEntity = constructHeader(token, null);
        log.info("break 1 " + " " + taskListsIds.size )

        var taskLists = mutableListOf<TaskList>();

        for ( task in taskListsIds ) {
            log.info("break 2 " )
            val events =
                restTemplate.exchange(
                    GOOGLE_TASKS_URI + "/" + task.taskListId,
                    HttpMethod.GET,
                    httpEntity,
                    TaskList::class.java
                )
            println("Tasks:" + events.body.toString())
            events.body
            taskLists.add(events.body!!);
        }

//        val taskLists = taskListsIds.map { task ->
//            {
//                log.info("break 2 " )
//                println("tasks!!!!!:" + task.toString())
//
//                val events =
//                    restTemplate.exchange(
//                        GOOGLE_TASKS_URI + "/" + task.taskListId,
//                        HttpMethod.GET,
//                        httpEntity,
//                        TaskResponse::class.java
//                    )
//                println("Tasks:" + events.body.toString())
//                events.body
//            }
//        }.toList()

        println("TTasks List:" + taskLists)

        return taskLists

//        val events =
//            restTemplate.exchange(GOOGLE_TASKS_URI, HttpMethod.GET, httpEntity, TaskResponse::class.java)
//        return events.body
    }

    fun createTaskList(token: String, userProfile: UserInfo?, taskList: TaskList?): TaskList? {


        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("id", taskList?.id)
        body.add("name", taskList?.title)

        val httpEntity = constructHeader(token, body);

        val events =
            restTemplate.exchange(
                GOOGLE_TASKS_URI,
                HttpMethod.POST,
                httpEntity,
                TaskListCreateResponse::class.java
            )
        val newTaskList = events.body
        println("Tasks:" + events.body.toString())

        taskRepository.save(TaskListEntity(null, userProfile!!.id, newTaskList!!.id as String))

        return newTaskList.mapToTaskList()
    }
}