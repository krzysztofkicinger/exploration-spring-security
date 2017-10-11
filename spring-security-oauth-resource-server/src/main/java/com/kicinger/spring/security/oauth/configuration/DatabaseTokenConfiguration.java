package com.kicinger.spring.security.oauth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@Profile("database-token")
public class DatabaseTokenConfiguration {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    /*
        Shared SQL backed token store, but authorization and resource servers are separate applications

        Resource Server needs to be able to check the validity of the access tokens
        issued by Authorization Server
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

}
