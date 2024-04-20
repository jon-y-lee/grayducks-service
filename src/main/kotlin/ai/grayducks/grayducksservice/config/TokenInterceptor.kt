package ai.grayducks.grayducksservice.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView


class TokenInterceptor(val restTemplate: RestTemplate) : HandlerInterceptor {

    /**
     * Executed before actual handler is executed
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        println("Token Interceptor: Pre Handle " + request.method)

        if (request.method.equals("OPTIONS")) return true;

        val token = request.getHeader("Authorization");

        println(" token: sss  " + token)
        if (token == null) {
            response.status = 400
            return false;
        }

        val urlPath: String = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + token.removePrefix("Bearer ");
        val verifierResponse: ResponseEntity<String> = restTemplate.getForEntity(urlPath, String::class.java)
        response.status = verifierResponse.statusCode.value()
        println("response status: " + response.status)
        val authRequest = UsernamePasswordAuthenticationToken(verifierResponse, token.removePrefix("Bearer "))

//        val authentication: Authentication = authenticationManager.authenticate(authRequest)
//        val securityContext = SecurityContextHolder.getContext()
//        securityContext.authentication = authRequest

        // Create a new session and add the security context.

        // Create a new session and add the security context.
//        val session = request.getSession(true)
//        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext)
//        println("response status:" + response.status)
        return response.status == HttpStatus.OK.value()
    }

    /**
     * Executed before after handler is executed. If view is a redirect view, we don't need to execute postHandle
     */
    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, @Nullable model: ModelAndView?) {
        System.out.println("post handle")
    }
}