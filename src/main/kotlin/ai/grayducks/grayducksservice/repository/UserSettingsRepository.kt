package ai.grayducks.grayducksservice.repository

import ai.grayducks.grayducksservice.repository.entities.UserSettingsEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSettingsRepository : JpaRepository<UserSettingsEntity, Long> {
    @EntityGraph(attributePaths = ["profiles"])
    fun findByExternalUserId(id: String): UserSettingsEntity
}