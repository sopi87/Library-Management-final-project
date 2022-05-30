package ro.siit.LibraryManagementProject.repository;

import ro.siit.LibraryManagementProject.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
