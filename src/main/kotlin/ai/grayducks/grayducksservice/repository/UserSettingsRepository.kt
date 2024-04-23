package ai.grayducks.grayducksservice.repository

import ai.grayducks.grayducksservice.repository.entities.Book
import ai.grayducks.grayducksservice.repository.entities.UserSettingsEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserSettingsRepository : JpaRepository<UserSettingsEntity, Long> {

    //    @EntityGraph(attributePaths = ["profiles"])
//    @Query("SELECT 1 FROM UserSettingsEntity")
//    @Query("Select * from user_settings_entity")
//    fun findByExternalUserId(id: String): UserSettingsEntity;

    @EntityGraph(attributePaths = ["profiles"])
    fun findByExternalUserId(id: String): UserSettingsEntity


//    @Query("SELECT distinct a.id, a.name, a.books FROM Book b left join Author a on a.id = b.author.id where a.id = 1")
//    fun findByIdAndProfilesOrderById(id: Long): List<Any>?

//    override fun findById(id: Long): Optional<UserSettingsEntity>


}