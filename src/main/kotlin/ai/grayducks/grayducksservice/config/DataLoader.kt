package ai.grayducks.grayducksservice.config

import ai.grayducks.grayducksservice.repository.AuthorRepository
import ai.grayducks.grayducksservice.repository.entities.Author
import ai.grayducks.grayducksservice.repository.entities.Book
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(val authorRepository: AuthorRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        System.out.println("Data loading");
        val author = Author(name = "J.K. Rowling")
        val book1 = Book(title = "Harry Potter and the Sorcerer's Stone", author = author)
        val book2 = Book(title = "Harry Potter and the Chamber of Secrets", author = author)

        author.books = listOf(book1, book2)
        authorRepository.save(author)
    }
}
