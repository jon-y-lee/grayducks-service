package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.domain.UserProfile
import ai.grayducks.grayducksservice.service.GoogleCalendarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
class ProfileController(@Autowired val googleCalendarService: GoogleCalendarService) {

    @GetMapping("/profile")
    fun ping(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<UserProfile> {

        return ResponseEntity.ok(googleCalendarService.getProfile(token));
    }
}