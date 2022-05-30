package ro.siit.LibraryManagementProject.controller;

import ro.siit.LibraryManagementProject.model.Book;
import ro.siit.LibraryManagementProject.model.Booking;

import ro.siit.LibraryManagementProject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;


   //request pt book
    @GetMapping("/book/{bookId}")
    public String addToCart(@PathVariable(name = "bookId") long bookId, Model model) {
        cartService.addBookInCart(bookId);
        model.addAttribute("booksInCart", cartService.viewCart());
        return "view_cart";
    }

    // request pt interogare
    @GetMapping("/view")
    public String viewCart(Model model) {
        model.addAttribute("booksInCart", cartService.viewCart());
        String message = "";
        model.addAttribute("message", message);
        return "view_cart";
    }

     // request pt delete
    @GetMapping("/remove/book/{bookId}")
    public String deleteBookFromCart(@PathVariable(name = "bookId") long bookId, Model model) {
        cartService.removeBookFromCart(bookId);
        model.addAttribute("booksInCart", cartService.viewCart());
        return "view_cart";
    }

    // request pt submit
    @PostMapping("/submit")
    public String submitCart(@RequestParam(name = "message") String message, Model model) {
        List<Book> books = cartService.viewCart();
        if (books.isEmpty()) {
            model.addAttribute("booksInCart", books);
            model.addAttribute("emptyCart", true);
            return "view_cart";
        }
        Booking booking = cartService.submitCart(message);
        model.addAttribute("message", "Your order is successfully placed");
        model.addAttribute("bookingId", booking.getId());
        return "order_completed";
    }
}
