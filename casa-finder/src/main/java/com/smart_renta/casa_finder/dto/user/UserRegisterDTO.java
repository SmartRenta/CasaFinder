package com.smart_renta.casa_finder.dto.user;

import com.smart_renta.casa_finder.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserRegisterDTO {

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
    private Number phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Getter
    @Setter
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should have at least 6 characters")
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String facebookUserName;

    @Getter
    @Setter
    private String instagramUserName;

    @Getter
    @Setter
    private UserType userType;
}