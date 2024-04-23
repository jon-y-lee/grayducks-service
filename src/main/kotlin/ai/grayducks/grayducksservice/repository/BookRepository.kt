package ai.grayducks.grayducksservice.repository

import ai.grayducks.grayducksservice.repository.entities.Book
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface BookRepository : JpaRepository<Book, Long> {
    // Fetch book by id along with the author
    @EntityGraph(attributePaths = ["author"])
    override fun findById(id: Long): Optional<Book>
}