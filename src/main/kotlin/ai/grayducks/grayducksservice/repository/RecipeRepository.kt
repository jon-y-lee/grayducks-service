package ai.grayducks.grayducksservice.repository

import ai.grayducks.grayducksservice.repository.entities.recipe.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : JpaRepository<RecipeEntity, Long> {
}