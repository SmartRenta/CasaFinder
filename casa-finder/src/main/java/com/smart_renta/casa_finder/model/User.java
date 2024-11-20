package com.smart_renta.casa_finder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Getter
    @Setter
    private String name;

    @NotBlank(message = "Last name is mandatory")
    @Getter
    @Setter
    private String lastName;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    @Getter
    @Setter
    private String description;

    @NotNull(message = "Phone number is mandatory")
    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private Number phone;

    @Getter
    @Setter
    private LocalDateTime registrationDate;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @Setter
    private String facebookUserName;

    @Getter
    @Setter
    private String instagramUserName;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should have at least 6 characters")
    @Getter
    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private DocumentType documentType;

    @Getter
    @Setter
    private Number documentNumber;

    @Getter
    @Setter
    private String imageUrl;

    // Constructors

    public User() {
    }

    public User(
            String name,
            String lastName,
            String description,
            Number phone,
            String email,
            String password,
            String facebookUserName,
            String instagramUserName,
            String userType, String documentType, Number documentNumber, String imageUrl) {
        this.name = name;
        this.lastName = lastName;
        this.description = description;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.facebookUserName = facebookUserName;
        this.instagramUserName = instagramUserName;
        this.userType = userType.equals("LANDLORD") ? UserType.LANDLORD : UserType.TENANT;
        this.documentType = documentType.equals("DNI") ? DocumentType.DNI : DocumentType.PASSPORT;
        this.documentNumber = documentNumber;
        this.imageUrl = imageUrl == null ? "https://cdn-icons-png.flaticon.com/512/1361/1361728.png" : imageUrl;

        this.registrationDate = LocalDateTime.now(ZoneId.of("GMT-5"));
    }
}