package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.config.Constants.Companion.GOOGLE_CALENDAR_EVENTS
import ai.grayducks.grayducksservice.config.Constants.Companion.GOOGLE_USERINFO_URI
import ai.grayducks.grayducksservice.domain.EventResponse
import ai.grayducks.grayducksservice.domain.UserInfo
import ai.grayducks.grayducksservice.repository.UserSettingsRepository
import ai.grayducks.grayducksservice.repository.entities.UserSettingsEntity
import ai.grayducks.grayducksservice.service.interfaces.HttpInterface
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GoogleCalendarService(
    @Autowired val userRepository: UserSettingsRepository,
    @Autowired val restTemplate: RestTemplate
) : HttpInterface {

    private val log = KotlinLogging.logger {}

    fun getUserProfile(token: String): UserInfo? {
        val httpEntity = constructHeader(token);
        val profile =
            restTemplate.exchange(GOOGLE_USERINFO_URI, HttpMethod.GET, httpEntity, UserInfo::class.java)

        log.info("Saving profile information:" + profile.body!!.id);

        updateUserVisitMetadata(profile.body!!);

        return profile.body
    }

    fun getEvents(token: String, startDate: String, endDate: String): EventResponse? {
        val httpEntity = constructHeader(token);
        val url = GOOGLE_CALENDAR_EVENTS + "?singleEvents=true&timeMin=" +
                startDate + "&timeMax=" + endDate
        val events =
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, EventResponse::class.java)

        return events.body
    }

    private fun updateUserVisitMetadata(profile: UserInfo) {
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