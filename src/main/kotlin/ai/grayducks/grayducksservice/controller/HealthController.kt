package ai.grayducks.grayducksservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@Controller
class HealthController {

    @GetMapping("/health")
    fun ping(@RequestHeader header: RequestHeader): ResponseEntity<String> {
//        System.out.println(SecurityContextHolder.getContext().authentication.credentials)
        return ResponseEntity.ok("ok")
    }
}