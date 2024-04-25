package ai.grayducks.grayducksservice.service.interfaces

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.util.MultiValueMap

interface HttpInterface {
    fun constructHeader(token: String, body: MultiValueMap<String, String>?): HttpEntity<*>? {
        val headers = HttpHeaders();
        headers.setBearerAuth(token.removePrefix("Bearer "))
        return HttpEntity(body, headers);
    }
}