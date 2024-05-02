package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.domain.Task
import ai.grayducks.grayducksservice.domain.TaskList
import ai.grayducks.grayducksservice.service.CalendarService
import ai.grayducks.grayducksservice.service.TasksService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
class TasksController(@Autowired val tasksService: TasksService, @Autowired val calendarService: CalendarService) {

    @GetMapping("/taskLists")
    fun getTasksLists(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<Any> {
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(tasksService.getTaskLists(token, userProfile));
    }

    @PostMapping("/taskLists")
    fun saveTaskist(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @RequestBody taskList: TaskList?
    ): ResponseEntity<TaskList> {
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(tasksService.createTaskList(token, userProfile, taskList));
    }

    @DeleteMapping("/taskLists/{id}")
    fun deleteTaskist(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @PathVariable id: String
    ): ResponseEntity<Any> {
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(tasksService.deleteTaskList(token, userProfile, id));
    }

    @GetMapping("/taskLists/{taskListId}/tasks")
    fun getTasksByTaskList(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @PathVariable taskListId: String
    ): ResponseEntity<Any> {
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(tasksService.getTasksByTaskListId(token, userProfile, taskListId));
    }

    @PutMapping("/taskLists/{taskListId}/tasks/{taskId}")
    fun updateTask(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String, @PathVariable taskListId: String,
        @PathVariable taskId: String, @RequestBody task: Task
    ): ResponseEntity<Any> {
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(tasksService.updateTask(token, userProfile, taskListId, taskId, task));
    }

    @PostMapping("/taskLists/{taskListId}/tasks")
    fun createTask(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @PathVariable taskListId: String,
        @RequestBody task: Task
    ): ResponseEntity<Any> {
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(tasksService.createTask(token, userProfile, taskListId, task));
    }
}