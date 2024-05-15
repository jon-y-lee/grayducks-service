package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.domain.Event
import ai.grayducks.grayducksservice.domain.EventResponse
import ai.grayducks.grayducksservice.service.CalendarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000", "https://www.launchprocedures.com", "https://www.grayducks.app"))
class EventsController(@Autowired val calendarService: CalendarService) {

    @GetMapping("/events")
    fun getEvents(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    @RequestParam("startTime") startTime: String,
    @RequestParam("endTime") endTime: String): ResponseEntity<EventResponse> {
        return ResponseEntity.ok(calendarService.getEvents(token, startTime, endTime));
    }

    @PostMapping("/events")
    fun createEvent(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
                  @RequestBody event: Event
    ): ResponseEntity<Event> {

        println("Event creation:" + event)
        return ResponseEntity.ok(calendarService.createEvent(token, event));
    }

    @PutMapping("/events/{id}")
    fun updateEvent(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
                    @PathVariable("id") id: String,
                    @RequestBody event: Event
    ): ResponseEntity<Event> {

        println("Event creation:" + event)
        return ResponseEntity.ok(calendarService.updateEvent(token, id, event));
    }

}