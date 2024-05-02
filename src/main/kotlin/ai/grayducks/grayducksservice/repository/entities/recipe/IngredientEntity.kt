package ai.grayducks.grayducksservice.repository.entities.recipe

import ai.grayducks.grayducksservice.repository.entities.UserSettingsEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
class IngredientEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = true)
    var name: String,

    @Column(nullable = true)
    var qty: String,

    @Column(nullable = true)
    var unit: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    var recipe: RecipeEntity? = null,

    ){
    constructor() : this(name = "", qty = "", unit = "") {}

}
