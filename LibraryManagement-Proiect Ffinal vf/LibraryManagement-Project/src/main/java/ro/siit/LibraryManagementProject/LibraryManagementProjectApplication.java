package ro.siit.LibraryManagementProject;

import ro.siit.LibraryManagementProject.model.Book;
import ro.siit.LibraryManagementProject.model.User;
import ro.siit.LibraryManagementProject.service.BookService;
import ro.siit.LibraryManagementProject.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibraryManagementProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementProjectApplication.class, args);
	}


	 /* Inserting data on runtime.
	 I am doing this because If I drop the DB, then this data will get inserted again.
	 */

	@Bean
	CommandLineRunner runner(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, BookService bookService) {
		return args -> {

			// Creating 2 users, one is admin (librariam), and one is member(borrower) in DB on runtime.

			User librarian = new User(); //librarian who is the admin
			librarian.setId(99);
			librarian.setFirstName("Adrian");
			librarian.setLastName("Soporan");
			librarian.setRole("librarian");
			librarian.setUsername("admin");
			librarian.setPassword(bCryptPasswordEncoder.encode("admin"));
			userService.insertRunTimeData(librarian);


			User member = new User(); //member of library, like a borrower
			member.setId(100);
			member.setFirstName("Claudia");
			member.setLastName("Soporan");
			member.setRole("member");
			member.setUsername("member");
			member.setPassword(bCryptPasswordEncoder.encode("member"));
			userService.insertRunTimeData(member);

			// Next I want to save 2 books in DB on runtime.

			Book book = new Book();
			book.setId(1);
			book.setBookTitle("Bambi");
			book.setBookAuthor("Egmont Romania");
			bookService.insertRunTimeData(book);

			Book book1 = new Book();
			book1.setId(2);
			book1.setBookTitle("Java from 0 to expert");
			book1.setBookAuthor("Stefan Tanasa");
			bookService.insertRunTimeData(book1);
		};
	}
}
