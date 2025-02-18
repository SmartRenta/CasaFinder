package com.smart_renta.casa_finder.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.smart_renta.casa_finder.model.Notification;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.resource.NotificationResource;
import com.smart_renta.casa_finder.service.NotificationService;
import com.smart_renta.casa_finder.service.UserService;
import com.smart_renta.casa_finder.util.JwtUtil;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/users/{userId}/notifications")
    public List<NotificationResource> getNotificationsById(@RequestHeader("Authorization") String token, @PathVariable Long userId){
        jwtUtil.validateToken(token);
        List<Notification> notifications = notificationService.getNotificationsById(userId);
        if(notifications.isEmpty()){
            User user = userService.findById(userId);
            Notification newNotif = notificationService.saveDefaultNotification(user);
            notifications.add(newNotif);
        }
        return notifications.stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @PostMapping("/notifications/{notificationId}/read")
    public boolean markAsRead(@RequestHeader("Authorization") String token, @PathVariable Long notificationId){
        
        jwtUtil.validateToken(token);
        return notificationService.markNotificationAsRead(notificationId);
    }

    private NotificationResource convertToResource(Notification entity){
        return mapper.map(entity, NotificationResource.class);
    }
}
