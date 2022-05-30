package ro.siit.LibraryManagementProject.repository;

import ro.siit.LibraryManagementProject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
