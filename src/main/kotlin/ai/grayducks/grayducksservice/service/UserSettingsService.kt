package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.domain.Profile
import ai.grayducks.grayducksservice.domain.UserInfo
import ai.grayducks.grayducksservice.domain.UserSettings
import ai.grayducks.grayducksservice.domain.mapToProfileEntity
import ai.grayducks.grayducksservice.repository.UserSettingsRepository
import ai.grayducks.grayducksservice.repository.entities.UserEntity
import ai.grayducks.grayducksservice.repository.entities.mapToUserSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserSettingsService(
    @Autowired val userSettingsRepository: UserSettingsRepository
) {

    fun getUserSettings(id: String): Any {
        return userSettingsRepository.findByExternalUserId(id)
    }

    fun addSettingsProfile(userProfile: UserInfo?, profile: Profile): UserSettings? {
        val userSetting = userSettingsRepository.findByExternalUserId(userProfile!!.id)
        val profileEntity: UserEntity = profile.mapToProfileEntity();
        profileEntity.usersettings = userSetting

        val profiles =
            userSetting.profiles.map { element -> element }.filter { element -> element.id.toString() != profile.id }.toMutableList()
        profiles.add(profile.mapToProfileEntity());
        userSetting.profiles = profiles
        return userSettingsRepository.save(userSetting).mapToUserSettings();
    }

    fun deleteSettingsProfile(userProfile: UserInfo?, id: String): UserSettings? {
        val userSetting = userSettingsRepository.findByExternalUserId(userProfile!!.id)
        var profiles =
            userSetting.profiles.map { profile -> profile }.filter { profile -> profile.id.toString() != id };
        userSetting.profiles = profiles;
        return userSettingsRepository.save(userSetting).mapToUserSettings()

    }
}