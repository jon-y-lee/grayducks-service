package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.domain.Profile
import ai.grayducks.grayducksservice.domain.User
import ai.grayducks.grayducksservice.domain.UserSettings
import ai.grayducks.grayducksservice.domain.mapToProfileEntity
import ai.grayducks.grayducksservice.repository.UserSettingsRepository
import ai.grayducks.grayducksservice.repository.entities.UserEntity
import ai.grayducks.grayducksservice.repository.entities.mapToUserSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserSettingsService(
    @Autowired val userSettingsRepository: UserSettingsRepository,
//    @Autowired val profileRepository: ProfileRepository
) {
    fun getUserSettings(id: String): Any {
        return userSettingsRepository.findByExternalUserId(id)
    }

    fun addSettingsProfile(userProfile: User?, profile: Profile): UserSettings? {
        val entity = userSettingsRepository.findByExternalUserId(userProfile!!.id)
        val profileEntity: UserEntity = profile.mapToProfileEntity();
        val lineItems = mutableListOf<UserEntity>()
        lineItems.addAll(entity.profiles)
        profileEntity.usersettings = entity
        lineItems.add(profileEntity)
        entity.profiles = lineItems
        val resp = userSettingsRepository.save(entity);
        return resp.mapToUserSettings();
    }

}