package ai.grayducks.grayducksservice.repository.entities

import ai.grayducks.grayducksservice.domain.UserSettings
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class UserSettingsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var externalUserId: String,

    @Column(nullable = false)
    var externalSystemName: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var emailAddress: String,

    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "profile_id")
    var profiles: List<UserEntity> = mutableListOf(),

    ) {
    constructor() : this(
        externalUserId = "", externalSystemName = "",
        name = "", emailAddress = "", profiles = listOf()
    ) {}
}

fun UserSettingsEntity.mapToUserSettings(): UserSettings {
    println("Mapppping : " + profiles.size + " " + profiles);
    return UserSettings(externalUserId, name, emailAddress, profiles.map { pe -> pe.mapToProfile() }.toMutableList())
}
