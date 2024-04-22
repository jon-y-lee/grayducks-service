package ai.grayducks.grayducksservice.repository.entities

import jakarta.persistence.*

@Entity
class UserEntity(
    @Column(nullable = false, unique = true)
    val externalId: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?=null,
) {
    public constructor() : this("", null)
}