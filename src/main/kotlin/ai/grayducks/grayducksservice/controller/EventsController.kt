package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.domain.EventResponse
import ai.grayducks.grayducksservice.domain.UserProfile
import ai.grayducks.grayducksservice.service.GoogleCalendarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
class EventsController(@Autowired val googleCalendarService: GoogleCalendarService) {

    @GetMapping("/events")
    fun ping(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    @RequestParam("startTime") startTime: String,
    @RequestParam("endTime") endTime: String): ResponseEntity<EventResponse> {

        return ResponseEntity.ok(googleCalendarService.getEvents(token, startTime, endTime));
    }
}