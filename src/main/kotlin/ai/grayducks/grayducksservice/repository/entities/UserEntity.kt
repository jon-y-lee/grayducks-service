package ai.grayducks.grayducksservice.repository.entities

import ai.grayducks.grayducksservice.domain.Profile
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var color: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    var usersettings: UserSettingsEntity? = null,
) {
    constructor() : this(name = "", color = "") {}
}

fun UserEntity.mapToProfile(): Profile {
    return Profile(id.toString(), name, color)
}
