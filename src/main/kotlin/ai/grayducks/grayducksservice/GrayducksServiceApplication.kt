package ai.grayducks.grayducksservice

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class GrayducksServiceApplication

fun main(args: Array<String>) {
	runApplication<GrayducksServiceApplication>(*args)
}
