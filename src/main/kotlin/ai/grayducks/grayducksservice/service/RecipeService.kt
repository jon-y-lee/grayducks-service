package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.domain.UserInfo
import ai.grayducks.grayducksservice.repository.RecipeRepository
import ai.grayducks.grayducksservice.repository.entities.recipe.RecipeEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RecipeService(@Autowired val recipeRepository: RecipeRepository) {

    fun search(userProfile: UserInfo?) : Page<RecipeEntity> {
        var pageable = Pageable.ofSize(100);
        return recipeRepository.findAll(pageable)
    }

    fun create(userProfile: UserInfo?, recipe: RecipeEntity): RecipeEntity? {
        recipe.createdOn = Instant.now()
        recipe.createdBy = userProfile?.id.toString()

        recipe.modifiedOn = Instant.now()
        recipe.modifiedBy = userProfile?.id.toString()

        return recipeRepository.save(recipe)
    }

    fun update(userProfile: UserInfo?, id: String, recipe: RecipeEntity): RecipeEntity? {
        val currentRecipe = recipeRepository.findById(id.toLong());

        if (currentRecipe.isEmpty) {
            return null;
        }

        recipe.modifiedOn = Instant.now()
        recipe.modifiedBy = userProfile?.id.toString()

        return recipeRepository.save(recipe);
    }

    fun delete(userProfile: UserInfo?, id: String): RecipeEntity? {
        val currentRecipe = recipeRepository.findById(id.toLong());
        var recipe: RecipeEntity;
        if (currentRecipe.isEmpty) {
            return null;
        } else {
            recipe = currentRecipe.get()
        }

        recipe.deletedOn = Instant.now()
        recipe.deletedBy = userProfile?.id.toString()

        return recipeRepository.save(recipe);
    }
}
