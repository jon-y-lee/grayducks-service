package ai.grayducks.grayducksservice.service.interfaces

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.util.MultiValueMap

interface HttpInterface {

    val objectMapper: ObjectMapper
        get() = ObjectMapper()

    fun constructHeader(token: String, body: Any?): HttpEntity<*>? {
        val headers = HttpHeaders();
        headers.setBearerAuth(token.removePrefix("Bearer "))

        var bodyString: String? = null

        if (body != null) {
          bodyString = objectMapper.writeValueAsString(body)
        }

        println("BodyString:" + bodyString);

        return HttpEntity(bodyString, headers);
    }
}