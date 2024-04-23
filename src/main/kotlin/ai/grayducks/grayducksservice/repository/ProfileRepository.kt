//package ai.grayducks.grayducksservice.repository
//
//import ai.grayducks.grayducksservice.repository.entities.UserEntity
//import ai.grayducks.grayducksservice.repository.entities.UserSettingsEntity
//import org.springframework.data.jpa.repository.EntityGraph
//import org.springframework.data.jpa.repository.JpaRepository
//import java.util.*
//
//interface ProfileRepository : JpaRepository<UserEntity, Long> {
//    @EntityGraph(attributePaths = ["usersettings"])
//    override fun findById(id: Long): Optional<UserEntity>
//
//}