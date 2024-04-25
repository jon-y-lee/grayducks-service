package ai.grayducks.grayducksservice.domain

import ai.grayducks.grayducksservice.repository.entities.UserEntity

data class UserSettings(
    val id: String?,
    val name: String?,
    val email: String?,
    val profiles: List<Profile>
) {}

data class Profile(val id: String?, val name: String, val color: String) {}

fun Profile.mapToProfileEntity(): UserEntity {
    return UserEntity(id?.toLong(), name, color, null)
}
