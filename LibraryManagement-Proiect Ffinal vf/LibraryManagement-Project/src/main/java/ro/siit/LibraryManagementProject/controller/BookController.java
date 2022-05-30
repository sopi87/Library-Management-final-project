package ro.siit.LibraryManagementProject.controller;

import ro.siit.LibraryManagementProject.model.Book;
import ro.siit.LibraryManagementProject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;


    @GetMapping("/add")
    public String addNewBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "add_book";
    }


    @PostMapping("/add")
    public String addNewBookSubmission(@Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "add_book";
        } else {
            bookService.saveBook(book);
            return "redirect:/";
        }
    }


    @GetMapping("/edit/{bookId}")
    public String editBook(@PathVariable(name = "bookId") Long bookId, Model model) {
        Optional<Book> book = bookService.getBookById(bookId);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "edit_book";
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("/edit/{bookId}")
    public String editBook(@Valid Book book, BindingResult result, @PathVariable(name = "bookId") long bookId) {
        if (result.hasErrors()) {
            return "edit_book";
        } else {
            Optional<Book> existingBook = bookService.getBookById(bookId);
            if (existingBook.isPresent()) {
                existingBook.get().setBookTitle(book.getBookTitle());
                existingBook.get().setBookAuthor(book.getBookAuthor());
                bookService.saveBook(existingBook.get());
                return "redirect:/";
            } else {
                return "edit_book";
            }
        }
    }


    @GetMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable(name = "bookId") long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/";
    }
}
