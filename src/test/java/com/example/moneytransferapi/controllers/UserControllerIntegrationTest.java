package com.example.moneytransferapi.controllers;

import com.example.moneytransferapi.dao.AccountRepository;
import com.example.moneytransferapi.dao.EmailDataRepository;
import com.example.moneytransferapi.dao.PhoneDataRepository;
import com.example.moneytransferapi.dao.UserRepository;
import com.example.moneytransferapi.entityes.Account;
import com.example.moneytransferapi.entityes.EmailData;
import com.example.moneytransferapi.entityes.PhoneData;
import com.example.moneytransferapi.entityes.User;
import com.example.moneytransferapi.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("money_transfer")
            .withUsername("postgres")
            .withPassword("password");

    @Container
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailDataRepository emailDataRepository;

    @Autowired
    private PhoneDataRepository phoneDataRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private User testUser;

    @BeforeEach
    void setUp() {
        emailDataRepository.deleteAll();
        phoneDataRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User();
        testUser.setName("User");
        testUser.setDateOfBirth(LocalDate.of(2026, 5, 30));
        testUser.setPassword(passwordEncoder.encode("User_password"));
        testUser = userRepository.save(testUser);

        Account account = new Account();
        account.setBalance(new BigDecimal("1000.00"));
        account.setInitialBalance(new BigDecimal("1000.00"));
        account.setUser(testUser);
        accountRepository.save(account);

        EmailData email = new EmailData();
        email.setEmail("test@mail.ru");
        email.setUser(testUser);
        emailDataRepository.save(email);

        PhoneData phone = new PhoneData();
        phone.setPhone("79207865432");
        phone.setUser(testUser);
        phoneDataRepository.save(phone);
    }

    private String getValidToken() {
        return jwtUtil.generateToken(testUser.getId());
    }

    @Test
    void searchUsersByNameReturnsUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .header("Authorization", "Bearer " + getValidToken())
                        .param("name", "User")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("User"));
    }

    @Test
    void searchUsersByEmailReturnsUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .header("Authorization", "Bearer " + getValidToken())
                        .param("email", "test@mail.ru"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].emails[0]").value("test@mail.ru"));
    }

    @Test
    void searchUsersByPhoneReturnsUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .header("Authorization", "Bearer " + getValidToken())
                        .param("phone", "79207865432"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].phones[0]").value("79207865432"));
    }

    @Test
    void searchUsersWithPaginationReturnsPageableResponse() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .header("Authorization", "Bearer " + getValidToken())
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(5));
    }
}