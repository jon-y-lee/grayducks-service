package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.domain.Profile
import ai.grayducks.grayducksservice.domain.UserInfo
import ai.grayducks.grayducksservice.domain.UserSettings
import ai.grayducks.grayducksservice.service.CalendarService
import ai.grayducks.grayducksservice.service.UserSettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000", "https://www.launchprocedures.com", "https://www.grayducks.app"))
class ProfileController(
    @Autowired val calendarService: CalendarService,
    @Autowired val userSettingsService: UserSettingsService
) {

    @GetMapping("/profile")
    fun profile(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<UserInfo> {
        return ResponseEntity.ok(calendarService.getUserProfile(token));
    }

    @GetMapping("/settings")
    fun settings(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<Any> {
        // TODO: Get id from jwt token
        val userProfile = calendarService.getUserProfile(token);
        return ResponseEntity.ok(userSettingsService.getUserSettings(userProfile!!.id))
    }

    @PutMapping("/settings/profiles")
    fun addSettingProfile(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @RequestBody profile: Profile
    ): ResponseEntity<UserSettings> {
        // TODO: Get id from jwt token
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(userSettingsService.addSettingsProfile(userProfile, profile))
    }

    @DeleteMapping("/settings/profiles/{id}")
    fun deleteSettingProfile(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @PathVariable("id") id: String
    ): ResponseEntity<UserSettings> {
        // TODO: Get id from jwt token
        val userProfile = calendarService.getUserProfile(token);

        return ResponseEntity.ok(userSettingsService.deleteSettingsProfile(userProfile, id))
    }
}