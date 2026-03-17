package com.github.alexmote04.tradesim.service;

import com.github.alexmote04.tradesim.model.Account;
import com.github.alexmote04.tradesim.model.User;
import com.github.alexmote04.tradesim.repository.AccountRepository;
import com.github.alexmote04.tradesim.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public User registerUser(String username, String email) {
        // Check if user already exists (Optional but good practice)
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create and save the User
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        User savedUser = userRepository.save(user);

        // Create and save their initial Fiat Account with $100,000 paper money
        Account account = new Account();
        account.setUser(savedUser);

        account.setBalance(new BigDecimal("100000.0000"));
        accountRepository.save(account);

        return savedUser;
    }
}