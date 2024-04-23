package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.domain.EventResponse
import ai.grayducks.grayducksservice.domain.User
import ai.grayducks.grayducksservice.repository.UserSettingsRepository
import ai.grayducks.grayducksservice.repository.entities.UserSettingsEntity
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GoogleCalendarService(
    @Autowired val userRepository: UserSettingsRepository,
    @Autowired val restTemplate: RestTemplate
) {

    private val log = KotlinLogging.logger {}

    val profileUrl = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json"
    val calendarEventsUrl = "https://www.googleapis.com/calendar/v3/calendars/primary/events"

    fun getUserProfile(token: String): User? {
        val httpEntity = constructHeader(token);
        val profile =
            restTemplate.exchange(profileUrl, HttpMethod.GET, httpEntity, User::class.java)
        log.info("Saving profile information:" + profile.body!!.id);

        updateUserVisitMetadata(profile.body!!);

        return profile.body
    }

    fun getEvents(token: String, startDate: String, endDate: String): EventResponse? {
        val httpEntity = constructHeader(token);
        val url = calendarEventsUrl + "?singleEvents=true&timeMin=" +
                startDate + "&timeMax=" + endDate
        val events =
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, EventResponse::class.java)

        return events.body
    }

    private fun constructHeader(token: String): HttpEntity<String> {
        val headers = HttpHeaders();
        headers.setBearerAuth(token.removePrefix("Bearer "))
        return HttpEntity("", headers);
    }

    private fun updateUserVisitMetadata(profile: User) {
        try {
            userRepository.findByExternalUserId(profile.id);
        } catch (e: EmptyResultDataAccessException) {
            var userSettingsEntity = UserSettingsEntity()
            userSettingsEntity.externalUserId = profile.id
            userSettingsEntity.name = profile.name
            userSettingsEntity.emailAddress = profile.email
            userSettingsEntity.externalSystemName = "Google"
            userRepository.save(userSettingsEntity)
        }
    }
}