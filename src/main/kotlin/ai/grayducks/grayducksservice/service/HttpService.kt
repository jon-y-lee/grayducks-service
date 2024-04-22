package ai.grayducks.grayducksservice.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders

interface HttpService {

    fun bar(): String {
        return "baz"
    }

    fun constructHeader(token: String): HttpEntity<*>? {
        val headers = HttpHeaders();
        headers.setBearerAuth(token.removePrefix("Bearer "))
        return HttpEntity("", headers);
    }

}