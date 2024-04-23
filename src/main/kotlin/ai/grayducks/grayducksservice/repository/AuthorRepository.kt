package ai.grayducks.grayducksservice.repository

import ai.grayducks.grayducksservice.repository.entities.Author
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AuthorRepository : JpaRepository<Author, Long> {
    // Fetch book by id along with the author
//    @EntityGraph(attributePaths = ["books"])
//    override fun findById(id: Long): Optional<Author>

//    @Query("SELECT distinct a.id, a.name, a.books FROM Book b left join Author a on a.id = b.author.id where a.id = 1")
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :authorId")
    fun findByIdWithBooks(authorId: Long): Author?

    @EntityGraph(attributePaths = ["books"])
    override fun findById(authorId: Long): Optional<Author>

//    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :authorId")
//    @Query("select c.id, c.name, c.books from Author c inner join c.books o where c.id = :authorId")
//    fun findByIdWithBooks(authorId: Long): List<Any>?
//    fun findByIdWithBooks(authorId: Long);

}