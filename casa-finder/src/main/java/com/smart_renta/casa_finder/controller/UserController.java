package com.smart_renta.casa_finder.controller;


import com.smart_renta.casa_finder.dto.user.UserDTO;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.service.UserService;
import com.smart_renta.casa_finder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public UserDTO getUser(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User user = userService.findByEmail(username);
        return new UserDTO(
                user.getName(),
                user.getLastName(),
                user.getDescription(),
                user.getPhone(),
                user.getRegistrationDate(),
                user.getEmail(),
                user.getFacebookUserName(),
                user.getInstagramUserName(),
                user.getUserType()
        );
    }

}
