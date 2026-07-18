package com.example.moneytransferapi.services;

import com.example.moneytransferapi.dao.EmailDataRepository;
import com.example.moneytransferapi.dao.PhoneDataRepository;
import com.example.moneytransferapi.dao.UserRepository;
import com.example.moneytransferapi.dto.UserResponse;
import com.example.moneytransferapi.entityes.EmailData;
import com.example.moneytransferapi.entityes.PhoneData;
import com.example.moneytransferapi.entityes.User;
import com.example.moneytransferapi.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final EmailDataRepository emailDataRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public Long authenticate(String login, String rawPassword) {
        User user = null;
        if (login.contains("@")) {
            EmailData emailData = emailDataRepository.findByEmail(login).orElse(null);
            if (emailData != null) user = emailData.getUser();
        }
        else {
            PhoneData phoneData = phoneDataRepository.findByPhone(login).orElse(null);
            if (phoneData != null) user = phoneData.getUser();
        }
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            log.info("User authenticated: {}", user.getId());
            return user.getId();
        }
        throw new BadCredentialsException("Invalid credentials");
    }

    @Cacheable(value = "users", key = "{#name, #email, #phone, #dateOfBirth, #page, #size}")
    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(String name, String email, String phone, LocalDate dateOfBirth, int page, int size) {
        Specification<User> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(root.get("name"), name + "%"));
            }
            if (dateOfBirth != null) {
                predicates.add(cb.greaterThan(root.get("dateOfBirth"), dateOfBirth));
            }
            if (email != null && !email.isEmpty()) {
                var emailJoin = root.join("emails");
                predicates.add(cb.equal(emailJoin.get("email"), email));
            }
            if (phone != null && !phone.isEmpty()) {
                var phoneJoin = root.join("phones");
                predicates.add(cb.equal(phoneJoin.get("phone"), phone));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return userRepository.findAll(spec, PageRequest.of(page, size))
                .map(userMapper::toResponse);
    }

    @CacheEvict(value = {"users"}, allEntries = true)
    @Transactional
    public void addEmail(Long userId, String email) {
        if (emailDataRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        EmailData emailData = new EmailData();
        emailData.setEmail(email);
        emailData.setUser(user);
        emailDataRepository.save(emailData);
        log.info("Email added for user: {}", userId);
    }

    @CacheEvict(value = {"users"}, allEntries = true)
    @Transactional
    public void updateEmail(Long userId, Long emailId, String newEmail) {
        if (emailDataRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("Email already taken");
        }
        EmailData emailData = emailDataRepository.findById(emailId)
                .orElseThrow(() -> new RuntimeException("Email not found"));
        if (!emailData.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not your email");
        }
        emailData.setEmail(newEmail);
        emailDataRepository.save(emailData);
        log.info("Email updated for user: {}", userId);
    }

    @CacheEvict(value = {"users"}, allEntries = true)
    @Transactional
    public void deleteEmail(Long userId, Long emailId) {
        EmailData emailData = emailDataRepository.findById(emailId)
                .orElseThrow(() -> new RuntimeException("Email not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getEmails().size() <= 1) {
            throw new RuntimeException("User must have at least one email");
        }
        if (!emailData.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not your email");
        }
        emailDataRepository.delete(emailData);
        log.info("Email deleted for user: {}", userId);
    }

    @CacheEvict(value = {"users"}, allEntries = true)
    @Transactional
    public void addPhone(Long userId, String phone) {
        if (phoneDataRepository.existsByPhone(phone)) {
            throw new RuntimeException("Phone already exists");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        PhoneData phoneData = new PhoneData();
        phoneData.setPhone(phone);
        phoneData.setUser(user);
        phoneDataRepository.save(phoneData);
        log.info("Phone added for user: {}", userId);
    }

    @CacheEvict(value = {"users"}, allEntries = true)
    @Transactional
    public void updatePhone(Long userId, Long phoneId, String newPhone) {
        if (phoneDataRepository.existsByPhone(newPhone)) {
            throw new RuntimeException("Phone already taken");
        }
        PhoneData phoneData = phoneDataRepository.findById(phoneId)
                .orElseThrow(() -> new RuntimeException("Phone not found"));
        if (!phoneData.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not your phone");
        }
        phoneData.setPhone(newPhone);
        phoneDataRepository.save(phoneData);
        log.info("Phone updated for user: {}", userId);
    }

    @CacheEvict(value = {"users"}, allEntries = true)
    @Transactional
    public void deletePhone(Long userId, Long phoneId) {
        PhoneData phoneData = phoneDataRepository.findById(phoneId)
                .orElseThrow(() -> new RuntimeException("Phone not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPhones().size() <= 1) {
            throw new RuntimeException("User must have at least one phone");
        }
        if (!phoneData.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not your phone");
        }
        phoneDataRepository.delete(phoneData);
        log.info("Phone deleted for user: {}", userId);
    }
}







