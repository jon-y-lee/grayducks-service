package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.domain.TaskResponse
import ai.grayducks.grayducksservice.service.GoogleTasksInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
class TasksController(@Autowired val googleTasksService: GoogleTasksInterface) {

    @GetMapping("/tasks")
    fun getTasks(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<TaskResponse> {
        return ResponseEntity.ok(googleTasksService.listTasks(token));
    }
}