package ai.grayducks.grayducksservice.domain

import ai.grayducks.grayducksservice.repository.entities.UserEntity

data class UserSettings(
    val id: String,
    val name: String?,
    val email: String?,
    val profiles: List<Profile>
) {}

data class Profile(val name: String, val color: String) {}

fun Profile.mapToProfileEntity(): UserEntity {
    var profileEntity = UserEntity()
    profileEntity.name = name
    profileEntity.color = color;
    return profileEntity
}
