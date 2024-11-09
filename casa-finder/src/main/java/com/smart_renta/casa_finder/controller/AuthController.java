package com.smart_renta.casa_finder.controller;

import com.smart_renta.casa_finder.dto.user.UserLoginDTO;
import com.smart_renta.casa_finder.dto.user.UserRegisterDTO;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.service.UserService;
import com.smart_renta.casa_finder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            String jwt = jwtUtil.generateToken(authentication);
            return "token: " + jwt;
        } catch (AuthenticationException e) {
            return "Error: " + e.getMessage();
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
                userDto.getUserType().name()
        );


        User savedUser = userService.save(user);
        return "User saved with id: " + savedUser.getId();
    }
}