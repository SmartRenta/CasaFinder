package com.smart_renta.casa_finder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_renta.casa_finder.model.Notification;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.repository.INotificationRepository;

@Service
public class NotificationService {

    @Autowired
    INotificationRepository notificationRepository;

    public List<Notification> getNotificationsById(Long userId){
        return notificationRepository.findByUserId(userId);
    }

    public Notification saveDefaultNotification(User user){
        Notification notification = new Notification();
        notification.setContent("CasaFinder te da la bienvenida!! Consigue contratos rápidos y seguros.");
        String route;
        switch(user.getUserType()){
            case LANDLORD:
                route = "/landlord/welcome";
                break;
            case TENANT:
                route = "/tenant/welcome";
                break;
            default: 
                route = "/error";
                break;
        }
        notification.setRoute(route);
        notification.setUser(user);
        notification.setCreationDate(LocalDateTime.now());
        notification.setRead(false);
        return notificationRepository.save(notification);
    }

    public boolean markNotificationAsRead(Long notificationId){
        Optional<Notification> notif = notificationRepository.findById(notificationId);
        if(notif.isPresent()){
            notif.get().setRead(true);
            Notification notif2 = notificationRepository.save(notif.get());
            System.out.println(">>>>>>>>>>>> notif2 read? : "+notif2.getRead());
            return notif2.getRead();
        }
        return false;
    }
}
