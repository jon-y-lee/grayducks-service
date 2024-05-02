package ai.grayducks.grayducksservice.domain

import java.time.Instant

data class RecipeUnused(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdOn: Instant,
    val modifiedBy: String,
    val modifiedOn: Instant,
) {
}

//fun Recipe.fromEntity(entity: RecipeEntity): Recipe {
//    return Recipe(
//        entity!!.id!!,
//        entity.title,
//        entity.createdBy,
//        entity.createdOn,
//        entity.modifiedBy,
//        entity.modifiedOn
//    );
//}