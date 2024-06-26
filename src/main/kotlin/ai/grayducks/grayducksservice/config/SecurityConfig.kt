package ai.grayducks.grayducksservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfig(@Autowired private val restTemplate: RestTemplate) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(TokenInterceptor(restTemplate))
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.securityMatcher("/**").authorizeHttpRequests { rmr ->
            rmr
                .requestMatchers("/**").permitAll()
        }.sessionManagement { smc ->
            smc
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.csrf { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }
        return http.build()
    }
}