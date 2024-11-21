package com.smart_renta.casa_finder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class configDataSource {

    @Value("${DB_USERNAME}")
    private String DBUsername;
    @Value("${DB_PASSWORD}")
    private String DBPassword;
    @Value("${DB_URL}")
    private String DBUrl;

    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        System.out.println(">>>>>>>>>> DBUrl: "+DBUrl);
        System.out.println(">>>>>>>>>> DBUsername: "+DBUsername);
        dataSourceBuilder.url(DBUrl);
        dataSourceBuilder.username(DBUsername);
        dataSourceBuilder.password(DBPassword);
        return dataSourceBuilder.build();
    }
}
