package com.mishinyura.books.services;

import com.mishinyura.books.models.User;
import com.mishinyura.books.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class UsersServiceImpl.
 * Implements Users Service.
 *
 * @author Mishin Yura (mishin.inbox@gmail.com)
 * @since 04.07.2022
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService {
    /**
     * Users Repository.
     */
    private final UsersRepository usersRepository;

    /**
     * Password Encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Method saves the user.
     *
     * @param user User
     */
    @Override
    @Transactional
    public void save(final User user) {
        var userToBeSaved = new User();
        userToBeSaved.setUsername(user.getUsername());
        userToBeSaved.setPassword(passwordEncoder.encode(user.getPassword()));
        userToBeSaved.setRole("ROLE_USER");
        usersRepository.save(userToBeSaved);
    }

    /**
     * Method finds user by username.
     *
     * @param username Username
     * @return Optional<User>
     */
    @Override
    public Optional<User> findByUsername(final String username) {
        return usersRepository.findByUsername(username);
    }
}
