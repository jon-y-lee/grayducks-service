package ai.grayducks.grayducksservice.repository.entities.recipe

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
class InstructionEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = true)
    var text: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    var recipe: RecipeEntity? = null,

    ){
    constructor() : this(text = "") {}

}
