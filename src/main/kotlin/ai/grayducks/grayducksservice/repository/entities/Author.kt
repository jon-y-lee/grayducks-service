package ai.grayducks.grayducksservice.repository.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class Author(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,

//    @OneToMany(mappedBy = "author", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
//    @JsonBackReference
    @JsonManagedReference
    @JoinColumn(name = "book_id")
    var books: List<Book> = mutableListOf(),

) {
    constructor() : this(name = "") {}
}