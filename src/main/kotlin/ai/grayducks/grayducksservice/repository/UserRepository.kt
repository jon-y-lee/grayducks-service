package ai.grayducks.grayducksservice.repository

import ai.grayducks.grayducksservice.repository.entities.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserEntity, Long> {


}