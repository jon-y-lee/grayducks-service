package ai.grayducks.grayducksservice.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.HandlerInterceptor

class TokenInterceptor(val restTemplate: RestTemplate) : HandlerInterceptor {

    private val log = KotlinLogging.logger {}

    private val TOKENINFO_URL = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token="

    /**
     * Executed before actual handler is executed
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        log.info("Token Interceptor: Pre Handle")
        System.out.println("TOKEN INTER")
        if (request.method.equals("OPTIONS")) return true;

        val token = request.getHeader("Authorization");

        if (token == null) {
            response.status = 400
            return false;
        }

        val urlPath: String =
            TOKENINFO_URL + token.removePrefix("Bearer ");
        val verifierResponse: ResponseEntity<String> = restTemplate.getForEntity(urlPath, String::class.java)
        response.status = verifierResponse.statusCode.value()

        log.info("response status: " + response.status)

        return response.status == HttpStatus.OK.value()
    }
}