package ro.siit.LibraryManagementProject.service;

import ro.siit.LibraryManagementProject.model.Booking;
import ro.siit.LibraryManagementProject.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;


    public Optional<Booking> getBookingById(long id) {
        return bookingRepository.findById(id);
    }


    public List<Booking> bookingsList(){
        return bookingRepository.findAll();
    }

    public Booking updateBooking(Booking booking){  // update pt cartile rezervate
        return bookingRepository.save(booking);
    }
}
