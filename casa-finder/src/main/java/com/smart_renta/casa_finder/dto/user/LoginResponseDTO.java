package com.smart_renta.casa_finder.dto.user;

import com.smart_renta.casa_finder.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LoginResponseDTO {
    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private UserType userType;
}