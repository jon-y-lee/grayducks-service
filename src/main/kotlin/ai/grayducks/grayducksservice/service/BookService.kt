package ai.grayducks.grayducksservice.service

import ai.grayducks.grayducksservice.repository.AuthorRepository
import ai.grayducks.grayducksservice.repository.BookRepository
import ai.grayducks.grayducksservice.repository.entities.Book
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(private val bookRepository: BookRepository,
                  private val authorRepository: AuthorRepository) {

    @Transactional(readOnly = true)
    fun getBookWithAuthor(bookId: Long): Any? {
//        return bookRepository.findById(bookId).orElse(null)
//        return authorRepository.findByIdWithBooks(bookId)
        return authorRepository.findById(bookId).get()
    }
}
