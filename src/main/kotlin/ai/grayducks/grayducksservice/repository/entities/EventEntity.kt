package ai.grayducks.grayducksservice.repository.entities

import jakarta.persistence.*

@Entity
class EventEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,

    @Column(nullable = false, unique = true) var externalId: String,

    @Column(nullable = true, unique = false)
    var assignedProfileId: String?,

    ) {
    constructor() : this(
        externalId = "",
        assignedProfileId = ""
    ) {
    };
}
