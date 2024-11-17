package com.smart_renta.casa_finder.resource;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResource {

    private Long id;
    private String content;
    private LocalDateTime creationDate;
    private Boolean read;
    private String route;
    private Long userId;

}