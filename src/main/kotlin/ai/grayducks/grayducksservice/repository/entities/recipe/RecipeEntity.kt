package ai.grayducks.grayducksservice.repository.entities.recipe

import ai.grayducks.grayducksservice.repository.entities.UserEntity
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.aspectj.apache.bcel.generic.Instruction
import java.time.Instant

@Entity
class RecipeEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,

    @Column(nullable = true) var title: String,

    @Column(nullable = true) var description: String,

    @Column(nullable = false) var createdBy: String?,

    @Column(nullable = false) var createdOn: Instant?,

    @Column(nullable = false) var modifiedBy: String?,

    @Column(nullable = false) var modifiedOn: Instant?,

    @Column(nullable = true) var deletedBy: String?,

    @Column(nullable = true) var deletedOn: Instant?,

    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "ingredient_id")
    var ingredients: List<IngredientEntity> = mutableListOf(),

    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "instruction_id")
    var instructions: List<InstructionEntity> = mutableListOf(),

    ) {
    constructor() : this(
        title = "",
        description = "",
        createdBy = "",
        createdOn = Instant.now(),
        modifiedBy = "",
        modifiedOn = Instant.now(),
        deletedBy = "",
        deletedOn = Instant.now()
    ) {
    };
}
