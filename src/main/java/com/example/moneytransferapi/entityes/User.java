package com.example.moneytransferapi.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Size(min = 8, max = 500)
    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Account account;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EmailData> emails = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<PhoneData> phones = new ArrayList<>();
}