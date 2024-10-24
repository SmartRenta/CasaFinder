package com.smart_renta.casa_finder.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configOpenAPI {
    @Bean
    public OpenAPI casaFinderOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Casa Finder API")
                        .description("Aplicación web para alquiler de casas"));
    }
}
