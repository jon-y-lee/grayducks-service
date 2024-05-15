package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.config.Constants
import ai.grayducks.grayducksservice.domain.EventResponse
import ai.grayducks.grayducksservice.service.CalendarService
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

data class TokenAuthorizationCodeRequest(
    val grant_type: String?,
    val code: String,
    val client_id: String?,
    val client_secret: String?,
    val redirect_uri: String?
){}

data class RefreshTokenAuthorizationCodeRequest(
    val grant_type: String?,
    val refresh_token: String?,
    val client_id: String?,
    val client_secret: String?,
    val redirect_uri: String?
){}

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000", "https://www.launchprocedures.com", "https://www.grayducks.app"))
class TokenController(
    @Autowired val calendarService: CalendarService,
    @Autowired val restTemplate: RestTemplate
) {

    val objectMapper: ObjectMapper
        get() = ObjectMapper()

    @PostMapping("/token")
    fun token(@RequestBody request: TokenAuthorizationCodeRequest,
    @Value("\${app.grayducks.redirectUrl:null}") redirectUrl: String): ResponseEntity<String> {
        val tokenCodeRequest = TokenAuthorizationCodeRequest(
            "authorization_code",
            request.code,
            "877315751810-m2qboe99fehv6roceg5f42tcatngqqc1.apps.googleusercontent.com",
            "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",
            redirectUrl
        )

        val httpEntity = constructHeader(tokenCodeRequest);
        val url = Constants.TOKEN_URI
        val events =
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, String::class.java)

        return ResponseEntity.ok(events.body);
    }

    @PostMapping("/token/refresh")
    fun refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) refreshToken: String,
                @Value("\${app.grayducks.redirectUrl:null}") redirectUrl: String,
    @RequestBody request: RefreshTokenAuthorizationCodeRequest): ResponseEntity<String> {

        println("Token code To be refresh: " + request)
        println("Token request request request: " + request.refresh_token)

        val refreshTokenCodeRequest = RefreshTokenAuthorizationCodeRequest(
            "refresh_token",
            request.refresh_token,
            "877315751810-m2qboe99fehv6roceg5f42tcatngqqc1.apps.googleusercontent.com",
            "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",
            redirectUrl
        )

        println("Refresh Code Object:" + refreshToken)

        val httpEntity = constructHeader(refreshTokenCodeRequest);
        val url = Constants.TOKEN_URI
        val events =
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, String::class.java)

        return ResponseEntity.ok(events.body);
    }

    fun constructHeader(body: Any?): HttpEntity<*>? {
        val headers = HttpHeaders();

        var bodyString: String? = null

        if (body != null) {
            bodyString = objectMapper.writeValueAsString(body)
        }

        println("BodyString:" + bodyString);

        return HttpEntity(bodyString, headers);
    }
}