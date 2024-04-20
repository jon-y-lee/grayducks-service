package ai.grayducks.grayducksservice.service

import com.google.api.services.calendar.Calendar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@Service
class GoogleCalendarService(@Autowired val restTemplate: RestTemplate) {

    val profileUrl = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json"
    val calendarEvents = "https://www.googleapis.com/calendar/v3/calendars/primary/events"

    fun getProfile(token: String) : UserProfile? {
        val headers = HttpHeaders();
        headers.setBearerAuth(token.removePrefix("Bearer "))
        val httpEntity = HttpEntity("", headers);
        val profile = restTemplate.exchange<UserProfile>(profileUrl, HttpMethod.GET, httpEntity, UserProfile::class.java)
        println("Profile found:" + profile)
        return profile.body
    }

    fun getEvents(token: String) : UserProfile? {
        val headers = HttpHeaders();
        headers.setBearerAuth(token.removePrefix("Bearer "))
        val httpEntity = HttpEntity("", headers);
        val profile = restTemplate.exchange<UserProfile>(profileUrl, HttpMethod.GET, httpEntity, UserProfile::class.java)
        return profile.body
    }

}
//
//import com.google.api.client.auth.oauth2.Credential
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
//import com.google.api.client.http.javanet.NetHttpTransport
//import com.google.api.client.json.JsonFactory
//import com.google.api.client.json.gson.GsonFactory
//import com.google.api.client.util.DateTime
//import com.google.api.client.util.store.FileDataStoreFactory
//import com.google.api.services.calendar.Calendar
//import com.google.api.services.calendar.CalendarScopes
//import com.google.api.services.calendar.model.Event
//import com.google.api.services.calendar.model.Events
//import org.springframework.aot.hint.TypeReference
//import org.springframework.aot.hint.TypeReference.listOf
//import org.springframework.stereotype.Service
//import java.io.*
//import java.security.GeneralSecurityException
//import java.util.*
//import java.util.List
//
//@Service
//class GoogleCalendarService {
//
//    /**
//     * Application name.
//     */
//    private val APPLICATION_NAME = "Google Calendar API Java Quickstart"
//
//    /**
//     * Global instance of the JSON factory.
//     */
//    private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
//
//    /**
//     * Directory to store authorization tokens for this application.
//     */
//    private val TOKENS_DIRECTORY_PATH = "tokens"
//
//    /**
//     * Global instance of the scopes required by this quickstart.
//     * If modifying these scopes, delete your previously saved tokens/ folder.
//     */
//    private val SCOPES: MutableList<TypeReference> = listOf(CalendarScopes.CALENDAR_READONLY)
//    private val CREDENTIALS_FILE_PATH = "/credentials.json"
//
//    /**
//     * Creates an authorized Credential object.
//     *
//     * @param HTTP_TRANSPORT The network HTTP Transport.
//     * @return An authorized Credential object.
//     * @throws IOException If the credentials.json file cannot be found.
//     */
//    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential? {
//        // Load client secrets.
//        val `in`: InputStream = GoogleCalendarService::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
//                ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
//        val clientSecrets: GoogleClientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`))
//
//        // Build flow and trigger user authorization request.
//        val flow: GoogleAuthorizationCodeFlow = Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build()
//        val receiver: LocalServerReceiver = Builder().setPort(8888).build()
//        //returns an authorized Credential object.
//        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
//    }
//
//    @kotlin.Throws(IOException::class, GeneralSecurityException::class)
//    @kotlin.jvm.JvmStatic
//    fun main(args: Array<String>) {
//        // Build a new authorized API client service.
//        val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
//        val service: Calendar = Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build()
//
//        // List the next 10 events from the primary calendar.
//        val now = DateTime(System.currentTimeMillis())
//        val events: Events = service.events().list("primary")
//                .setMaxResults(10)
//                .setTimeMin(now)
//                .setOrderBy("startTime")
//                .setSingleEvents(true)
//                .execute()
//        val items: List<Event> = events.getItems()
//        if (items.isEmpty()) {
//            println("No upcoming events found.")
//        } else {
//            println("Upcoming events")
//            for (event in items) {
//                var start: DateTime = event.getStart().getDateTime()
//                if (start == null) {
//                    start = event.getStart().getDate()
//                }
//                System.out.printf("%s (%s)\n", event.getSummary(), start)
//            }
//        }
//    }
//
//}