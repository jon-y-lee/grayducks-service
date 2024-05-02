package ai.grayducks.grayducksservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DataLoader {


    @Autowired
    fun init() {
        println("Init data loader")
    }
}