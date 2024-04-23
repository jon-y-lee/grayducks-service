package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.domain.TaskResponse
import ai.grayducks.grayducksservice.service.interfaces.HttpInterface
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GoogleTasksInterface(@Autowired val restTemplate: RestTemplate) : HttpInterface {

    private val log = KotlinLogging.logger {}

    val GOOGLE_TASKS_URI = "https://tasks.googleapis.com/tasks/v1/users/@me/lists"

    fun listTasks(token: String): TaskResponse? {
        log.info("Tasks:" + token)

        val httpEntity = constructHeader(token);
        val events =
            restTemplate.exchange(GOOGLE_TASKS_URI, HttpMethod.GET, httpEntity, TaskResponse::class.java)
        val eventString =
            restTemplate.exchange(GOOGLE_TASKS_URI, HttpMethod.GET, httpEntity, String::class.java)
        log.info(eventString.body)
        return events.body
    }
}