package ai.grayducks.grayducksservice.repository.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
class Book(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    var author: Author? = null,
) {
    constructor() : this(title = "") {

    }
}