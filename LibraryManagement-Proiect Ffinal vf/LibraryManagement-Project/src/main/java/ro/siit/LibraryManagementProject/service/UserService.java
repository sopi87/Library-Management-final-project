package ro.siit.LibraryManagementProject.service;

import ro.siit.LibraryManagementProject.model.User;
import ro.siit.LibraryManagementProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder; //It's for password encryption
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * In this I am fetching the users by their role.
     * Like If I'll pass the role as member then it will give me all those users who have role member.
     * @param role
     * @return
     */
    public List<User> getUsersByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    /**
     * In this function, I'm registering the users.
     * the password encoder is for the spring security, because spring security wants our password encrypted.
     * @param user
     * @return
     */
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * This function is getting used to update the user.
     * @param user
     * @return
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Here I'm fetching the user by their username.
     * This will return me the user who have the username that I'll enter.
     * @param username
     * @return
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    /**
     * This function is getting used for the insertion at runtime.
     * Here I'm checking whether the user already exists with the same username or not.
     * If it doesn't exist then insert the user in the database.
     * @param user
     */
    public void insertRunTimeData(User user) {
        Optional<User> user1 = userRepository.findUserByUsername(user.getUsername());
        if (!user1.isPresent()) {
            userRepository.saveAndFlush(user);
        }
    }

    /**
     * This function is the key function of spring security.
     * Spring security uses this function to find the user from the database and authenticate it.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    getAuthority(user)
            );
        } else {
            throw new RuntimeException("User doesn't exists");
        }
    }

    /**
     * This function is providing the role of the user that we have in the datatabase.
     * @param user
     * @return
     */
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }
}
