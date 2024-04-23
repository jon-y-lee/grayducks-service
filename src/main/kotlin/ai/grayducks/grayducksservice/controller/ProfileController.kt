package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.domain.Profile
import ai.grayducks.grayducksservice.domain.User
import ai.grayducks.grayducksservice.domain.UserSettings
import ai.grayducks.grayducksservice.service.BookService
import ai.grayducks.grayducksservice.service.GoogleCalendarService
import ai.grayducks.grayducksservice.service.UserSettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
class ProfileController(
    @Autowired val googleCalendarService: GoogleCalendarService,
    @Autowired val userSettingsService: UserSettingsService,
    @Autowired val bookService: BookService,
) {

    @GetMapping("/profile")
    fun profile(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<User> {
        return ResponseEntity.ok(googleCalendarService.getUserProfile(token));
    }

    @GetMapping("/settings")
    fun settings(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<Any> {
        // TODO: Get id from jwt token
        val userProfile = googleCalendarService.getUserProfile(token);
        return ResponseEntity.ok(userSettingsService.getUserSettings(userProfile!!.id))
    }


    @GetMapping("/book/{id}")
    fun getBook(@PathVariable id: Long): ResponseEntity<Any> {
        val book = bookService.getBookWithAuthor(id)
        return if (book != null) {
            ResponseEntity.ok(book)
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @PutMapping("/settings/profiles")
    fun addSettingProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String, @RequestBody profile: Profile): ResponseEntity<UserSettings> {
        // TODO: Get id from jwt token
        val userProfile = googleCalendarService.getUserProfile(token);
        println("User Profilel: " + userProfile);
        return ResponseEntity.ok(userSettingsService.addSettingsProfile(userProfile, profile))
    }

}