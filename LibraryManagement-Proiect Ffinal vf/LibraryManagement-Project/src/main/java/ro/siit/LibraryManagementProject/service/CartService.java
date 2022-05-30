package ro.siit.LibraryManagementProject.service;

import ro.siit.LibraryManagementProject.model.Book;
import ro.siit.LibraryManagementProject.model.Booking;
import ro.siit.LibraryManagementProject.model.User;
import ro.siit.LibraryManagementProject.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    BookingRepository bookingRepository;

    /*
     * With hashmap I'm storing the books.
     * Using hashmap because it will store everytime a unique book, so it will not add same book multiple times.
     */
    HashMap<Long, Book> bookHashMap = new HashMap<>();


    public void addBookInCart(long bookId) { //metoda pt adaugat books --> click borrow
        Optional<Book> book = bookService.getBookById(bookId);
        if (book.isPresent()) {
            bookHashMap.put(bookId, book.get());
        } else {
            throw new RuntimeException("There is no book with this Id");
        }
    }


    public void removeBookFromCart(long bookId) {  //metoda pt delete books --> click remoev
        if (bookHashMap.containsKey(bookId)) {
            bookHashMap.remove(bookId);
        } else {
            throw new RuntimeException("There is no book in the cart with this Id");
        }
    }

    public List<Book> viewCart() { //list books din cart
        List<Book> books = new ArrayList<>();
        bookHashMap.forEach(
                (key, value) -> books.add(value));
        return books;
    }


    public Booking submitCart(String message) { //metoda pt submit cart si stamp date
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Book> books = viewCart();
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setBooks(books);
        booking.setStatus("pending");
        booking.setMessage(message);
        Booking savedBooking = bookingRepository.save(booking);

        Optional<User> user = userService.getUserByUsername(username);
        user.ifPresent(value -> value.getBookings().add(savedBooking));
        userService.updateUser(user.get());
        bookHashMap = new HashMap<>();
        return savedBooking;
    }
}
