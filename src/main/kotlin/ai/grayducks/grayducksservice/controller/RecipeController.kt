package ai.grayducks.grayducksservice.controller

import ai.grayducks.grayducksservice.repository.entities.recipe.RecipeEntity
import ai.grayducks.grayducksservice.service.CalendarService
import ai.grayducks.grayducksservice.service.RecipeService
import ai.grayducks.grayducksservice.service.UserSettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Controller
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
class RecipeController(
    @Autowired val calendarService: CalendarService,
    @Autowired val recipeService: RecipeService,
    @Autowired val userSettingsService: UserSettingsService
) {

    @PostMapping("/recipes/search")
    fun search(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<Page<RecipeEntity>> {
        val userProfile = calendarService.getUserProfile(token);
        return ResponseEntity.ok(recipeService.search(userProfile));
    }

    @PostMapping("/recipes")
    fun create(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @RequestBody recipe: RecipeEntity
    ): ResponseEntity<RecipeEntity> {
        val userProfile = calendarService.getUserProfile(token);
        return ResponseEntity.ok(recipeService.create(userProfile, recipe));
    }

    @PutMapping("/recipes/{id}")
    fun update(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @PathVariable("id") id: String,
        @RequestBody recipe: RecipeEntity
    ): ResponseEntity<RecipeEntity> {
        val userProfile = calendarService.getUserProfile(token);
        return ResponseEntity.ok(recipeService.update(userProfile, id, recipe));
    }

    @DeleteMapping("/recipes/{id}")
    fun delete(
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
        @PathVariable("id") id: String
    ): ResponseEntity<RecipeEntity> {
        val userProfile = calendarService.getUserProfile(token);
        return ResponseEntity.ok(recipeService.delete(userProfile, id));
    }
}