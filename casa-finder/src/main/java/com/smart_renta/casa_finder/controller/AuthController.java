package com.smart_renta.casa_finder.controller;

import com.smart_renta.casa_finder.dto.user.LoginResponseDTO;
import com.smart_renta.casa_finder.dto.user.UserLoginDTO;
import com.smart_renta.casa_finder.dto.user.UserRegisterDTO;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.service.NotificationService;
import com.smart_renta.casa_finder.service.UserService;
import com.smart_renta.casa_finder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserLoginDTO user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            String jwt = jwtUtil.generateToken(authentication);
            User loggedInUser = userService.findByEmail(user.getEmail());
            return new LoginResponseDTO(jwt, loggedInUser.getUserType());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterDTO userDto) {
        
        User user = new User(
                userDto.getName(),
                userDto.getLastName(),
                userDto.getDescription(),
                userDto.getPhone(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFacebookUserName(),
                userDto.getInstagramUserName(),
                userDto.getUserType(),
                userDto.getDocumentType(),
                userDto.getDocumentNumber(),
                userDto.getImageUrl()
        );

        User savedUser = userService.save(user);

        if(savedUser.getId() > 0){
            notificationService.saveDefaultNotification(savedUser);
        }

        return "User saved with id: " + savedUser.getId();
    }

    public String validateToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid token format");
        }

        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Invalid token");
        }

        return username;
    }
}