package ro.siit.LibraryManagementProject.controller;

import ro.siit.LibraryManagementProject.model.Booking;
import ro.siit.LibraryManagementProject.model.User;
import ro.siit.LibraryManagementProject.service.BookingService;
import ro.siit.LibraryManagementProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    UserService userService;
    @Autowired
    BookingService bookingService;

    @GetMapping("/all")
    public String allOrdersFromMembers(Model model) {
        model.addAttribute("bookingsList", bookingService.bookingsList());
        return "member_book_orders";
    }


    @GetMapping("/booking/accept/{bookingId}")
    public String acceptBookingByBookingId(@PathVariable(name = "bookingId") long id, Model model) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            booking.get().setStatus("accepted");
            bookingService.updateBooking(booking.get());
            return "redirect:/bookings/all";
        } else {
            return "redirect:/";
        }
    }



    @GetMapping("/booking/reject/{bookingId}")
    public String rejectBookingByBookingId(@PathVariable(name = "bookingId") long id, Model model) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            booking.get().setStatus("rejected");
            bookingService.updateBooking(booking.get());
            return "redirect:/bookings/all";
        } else {
            return "redirect:/";
        }
    }


    @GetMapping("/user")
    public String getSpecificUserOrders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            model.addAttribute("bookingsList", user.get().getBookings());
            return "member_book_orders";
        } else {
            return "redirect:/";
        }
    }


    @GetMapping("/booking/{bookingId}")
    public String bookingDetailsById(@PathVariable(name = "bookingId") long id, Model model) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            model.addAttribute("bookingDetails", booking.get().getBooks());
            return "booking_details";
        } else {
            return "redirect:/";
        }
    }
}
