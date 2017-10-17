package com.kicinger.spring.security.oauth.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
@Profile({"database-token", "remote-token"})
public class Oauth2ResourceServerConfiguration {
}
