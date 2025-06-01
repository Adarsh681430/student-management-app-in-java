package service;

import model.User;
import model.Role;
import repository.UserRepository;

import java.util.Optional;

public class AuthService {
    private UserRepository userRepository;
    private long idCounter = 1;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Signup method
    public String signup(String name, String email, String password, String roleStr) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return "User already exists with this email.";
        }

        Role role = Role.valueOf(roleStr.toUpperCase());
        User newUser = new User(idCounter++, name, email, password, role);
        userRepository.save(newUser);
        return "User registered successfully!";
    }

    // Login method
    public String login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return "User not found.";
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            return "Invalid password.";
        }

        return "Login successful! Welcome, " + user.getName() + " (" + user.getRole() + ")";
    }
}