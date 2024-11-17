package com.smart_renta.casa_finder.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_renta.casa_finder.model.Notification;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.resource.NotificationResource;
import com.smart_renta.casa_finder.resource.SaveNotificationResource;
import com.smart_renta.casa_finder.service.NotificationService;
import com.smart_renta.casa_finder.service.UserService;
import com.smart_renta.casa_finder.util.JwtUtil;

@RestController
@RequestMapping("api/v1")
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
        System.out.println("notif size: "+notifications.size());
        if(notifications.isEmpty()){
            User user = userService.findById(userId);
            Notification newNotif = notificationService.saveDefaultNotification(user);
            System.out.println("se agregó nva notificacion con id:"+newNotif.getId());
            notifications.add(newNotif);
        }
        return notifications.stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @PostMapping("/notifications/{notificationId}/read")
    public boolean markAsRead(@RequestHeader("Authorization") String token, @PathVariable Long notificationId){

        System.out.println(">>>>> notifId: "+notificationId);
        return notificationService.markNotificationAsRead(notificationId);
    }

    private NotificationResource convertToResource(Notification entity){
        return mapper.map(entity, NotificationResource.class);
    }
    private Notification convertToEntity(SaveNotificationResource resource){
        return mapper.map(resource, Notification.class);
    }
}
