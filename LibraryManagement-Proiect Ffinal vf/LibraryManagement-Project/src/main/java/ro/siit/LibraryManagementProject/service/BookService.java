package ro.siit.LibraryManagementProject.service;

import ro.siit.LibraryManagementProject.model.Book;
import ro.siit.LibraryManagementProject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }


    public List<Book> listOfBooks() { //metoda pt lista carti din DB
        return bookRepository.findAll();
    }


    public Optional<Book> getBookById(long id) { //
        return bookRepository.findById(id);
    }


    public void deleteBook(long bookId) {     // delete book from DB
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
        } else {
            throw new RuntimeException("There is no book with this ID");
        }
    }

    /**
     * This method is getting used for the insertion at runtime.
     * Here I'm checking whether the book already exists with the same id or not.
     * If it doesn't exist --> insert the book in the database.
     *
     * @param book
     */
    public void insertRunTimeData(Book book) {
        Optional<Book> book1 = bookRepository.findById(book.getId());
        if (!book1.isPresent()) {
            bookRepository.saveAndFlush(book);
        }
    }
}
