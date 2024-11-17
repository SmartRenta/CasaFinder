package com.smart_renta.casa_finder.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveNotificationResource {

    private String content;
    private String route;
    private Long userId;

}