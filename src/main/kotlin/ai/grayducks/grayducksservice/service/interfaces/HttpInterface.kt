package ai.grayducks.grayducksservice.service.interfaces

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

interface HttpInterface {
    
    fun constructHeader(token: String): HttpEntity<*>? {
        val headers = HttpHeaders();
        headers.setBearerAuth(token.removePrefix("Bearer "))
        return HttpEntity("", headers);
    }

}