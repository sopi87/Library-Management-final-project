package ro.siit.LibraryManagementProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Column(name = "first_name")
    @NotBlank(message = "First Name should not be blank")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password should not be blank")
    @JsonIgnore
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "Username should not be blanked")
    private String username;

    @Column(name = "role")
    private String role;

    @OneToMany(targetEntity = Booking.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Booking> bookings = new ArrayList<>();

}
