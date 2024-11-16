package com.smart_renta.casa_finder.controller;


import com.smart_renta.casa_finder.dto.user.UserDTO;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.service.UserService;
import com.smart_renta.casa_finder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public UserDTO getUser(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            User user = userService.findByEmail(username);
            return new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getLastName(),
                    user.getDescription(),
                    user.getPhone(),
                    user.getRegistrationDate(),
                    user.getEmail(),
                    user.getFacebookUserName(),
                    user.getInstagramUserName(),
                    user.getUserType(),
                    user.getDocumentType(),
                    user.getDocumentNumber(),
                    user.getImageUrl()
            );
        } else {
            throw new IllegalArgumentException("Invalid token format");
        }
    }

    @PutMapping("/")
    public UserDTO updateUser(@RequestHeader("Authorization") String token, @RequestBody UserDTO userDTO) {
        if (token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            User user = userService.findByEmail(username);

            user.setName(userDTO.getName());
            user.setLastName(userDTO.getLastName());
            user.setDescription(userDTO.getDescription());
            user.setPhone(userDTO.getPhone());
            user.setEmail(userDTO.getEmail());
            user.setFacebookUserName(userDTO.getFacebookUserName());
            user.setInstagramUserName(userDTO.getInstagramUserName());
            user.setUserType(userDTO.getUserType());
            user.setDocumentType(userDTO.getDocumentType());
            user.setDocumentNumber(userDTO.getDocumentNumber());
            user.setImageUrl(userDTO.getImageUrl());
            userService.update(user);
            return new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getLastName(),
                    user.getDescription(),
                    user.getPhone(),
                    user.getRegistrationDate(),
                    user.getEmail(),
                    user.getFacebookUserName(),
                    user.getInstagramUserName(),
                    user.getUserType(),
                    user.getDocumentType(),
                    user.getDocumentNumber(),
                    user.getImageUrl()
            );
        } else {
            throw new IllegalArgumentException("Invalid token format");
        }
    }
}
