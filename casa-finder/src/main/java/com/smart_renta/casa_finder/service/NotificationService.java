package com.smart_renta.casa_finder.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart_renta.casa_finder.model.Contract;
import com.smart_renta.casa_finder.model.Notification;
import com.smart_renta.casa_finder.model.Property;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.repository.INotificationRepository;

@Service
public class NotificationService {

    @Autowired
    INotificationRepository notificationRepository;

    public List<Notification> getNotificationsById(Long userId){
        return notificationRepository.findByUserId(userId);
    }

    public Notification saveNotification(String content, String route, User user){
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setRoute(route);
        notification.setUser(user);
        notification.setCreationDate(LocalDateTime.now(ZoneId.of("GMT-5")));
        notification.setRead(false);
        return notificationRepository.save(notification);
    }

    public Notification saveDefaultNotification(User user){
        
        String content = "CasaFinder te da la bienvenida!! Consigue contratos rápidos y seguros.";
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
        return saveNotification(content, route, user);
    }

    public Notification saveTenantContractResponse(Property property, User landlord, User tenant, Contract contract, boolean accepted){
        String content = String.format("Su propuesta de contrato para %s ha sido %s por %s %s", 
        property.getTitle(), accepted?"aceptada":"rechazada", landlord.getName(), landlord.getLastName());
        String route = "/tenant/contratos/"+contract.getId();
        return saveNotification(content, route, tenant);
    }

    public Notification saveLandlordContractRequest(User tenant, Property property, User landlord, Contract contract){
        String content = String.format("Revisa la solicitud de propuesta que ha generado %s %s para tu propiedad %s.", tenant.getName(), tenant.getLastName(), property.getTitle());
        String route = "/landlord/contratos/"+contract.getId();
        return saveNotification(content, route, landlord);
    }

    public boolean markNotificationAsRead(Long notificationId){
        Optional<Notification> notif = notificationRepository.findById(notificationId);
        if(notif.isPresent()){
            notif.get().setRead(true);
            Notification notif2 = notificationRepository.save(notif.get());
            return notif2.getRead();
        }
        return false;
    }
}
