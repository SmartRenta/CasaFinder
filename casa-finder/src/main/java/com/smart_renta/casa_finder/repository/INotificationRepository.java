package com.smart_renta.casa_finder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_renta.casa_finder.model.Notification;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long>{
    List<Notification> findByUserId(Long userId);
}