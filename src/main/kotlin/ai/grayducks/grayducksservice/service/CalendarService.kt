package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.config.Constants.Companion.GOOGLE_CALENDAR_EVENTS
import ai.grayducks.grayducksservice.config.Constants.Companion.GOOGLE_USERINFO_URI
import ai.grayducks.grayducksservice.domain.Event
import ai.grayducks.grayducksservice.domain.EventResponse
import ai.grayducks.grayducksservice.domain.UserInfo
import ai.grayducks.grayducksservice.repository.EventRepository
import ai.grayducks.grayducksservice.repository.UserSettingsRepository
import ai.grayducks.grayducksservice.repository.entities.EventEntity
import ai.grayducks.grayducksservice.repository.entities.UserSettingsEntity
import ai.grayducks.grayducksservice.service.interfaces.HttpInterface
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.Collections
import java.util.stream.Collectors

@Service
class CalendarService(
    @Autowired val userRepository: UserSettingsRepository,
    @Autowired val eventRepository: EventRepository,
    @Autowired val restTemplate: RestTemplate
) : HttpInterface {

    private val log = KotlinLogging.logger {}

    fun getUserProfile(token: String): UserInfo? {
        val httpEntity = constructHeader(token, null);
        val profile =
            restTemplate.exchange(GOOGLE_USERINFO_URI, HttpMethod.GET, httpEntity, UserInfo::class.java)

        log.info("Saving profile information:" + profile.body!!.id);

        updateUserVisitMetadata(profile.body!!);

        return profile.body
    }

    fun getEvents(token: String, startDate: String, endDate: String): EventResponse? {
        val httpEntity = constructHeader(token, null);
        val url = GOOGLE_CALENDAR_EVENTS + "?singleEvents=true&timeMin=" +
                startDate + "&timeMax=" + endDate
        val events =
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, EventResponse::class.java)

        println("Events:" + events.body.toString())
        for (item in events.body?.items!!) {
            val eventEntity = eventRepository.findByExternalId(item?.id!!);

            if ( eventEntity != null) {
                item.assigneeId = eventEntity.assignedProfileId
            }
        }

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

    fun createEvent(token: String, event: Event): Event? {
        val httpEntity = constructHeader(token, event);
        val url = GOOGLE_CALENDAR_EVENTS
        val events =
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, Event::class.java)

        if (event.assigneeId != null) {
            // save event
            val eventEntity = EventEntity()
            eventEntity.externalId = events?.body?.id.toString();
            eventEntity.assignedProfileId = event.assigneeId;
            eventRepository.save(eventEntity);
        }

        events.body?.assigneeId = event.assigneeId

        return events.body
    }

    fun updateEvent(token: String, id: String, event: Event): Event? {
        val httpEntity = constructHeader(token, event);
        val url = GOOGLE_CALENDAR_EVENTS + "/" + id
        val events =
            restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Event::class.java)

        println("Events Updated:" + events.toString())
        println("  assignee:" + event.assigneeId)

        if (event.assigneeId != null) {
            // save event
            var eventEntity: EventEntity? = eventRepository.findByExternalId(events?.body?.id.toString())
            if (eventEntity != null) {
                eventEntity?.assignedProfileId = event.assigneeId;
                println("Event saving assigned profid")
                eventRepository.save(eventEntity);
            } else {
                val newEventEntity: EventEntity = EventEntity();
                newEventEntity.assignedProfileId = event.assigneeId;
                newEventEntity.externalId = id
                println("Event not found - creating one")
                eventRepository.save(newEventEntity);
            }
        }

        events.body?.assigneeId = event.assigneeId

        return events.body

    }
}