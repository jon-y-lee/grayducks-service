package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.domain.TaskList
import ai.grayducks.grayducksservice.service.CalendarService
import ai.grayducks.grayducksservice.service.TasksService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
class TasksController(@Autowired val tasksService: TasksService, @Autowired val calendarService: CalendarService) {

    @GetMapping("/taskLists")
    fun getTasks(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<Any> {
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(tasksService.getTaskLists(token, userProfile));
    }

    @PostMapping("/taskLists")
    fun saveTaskist(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String, @RequestBody taskList: TaskList ): ResponseEntity<TaskList> {
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(tasksService.createTaskList(token, userProfile, taskList));
    }

}