package com.kicinger.spring.security.oauth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@Profile("remote-token")
public class RemoteTokenConfiguration {


    /*
        This RemoteTokenService will use CheckTokenEndPoint on
        Authorization Server to validate AccessToken and obtain Authentication object from it.

        The Authorization Server can use any TokenStore type [JdbcTokenStore, JwtTokenStore, …] –
        this won’t affect the RemoteTokenService or Resource server.
     */
    @Bean
    public RemoteTokenServices tokenStore() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:9000/oauth/check_token");
        tokenService.setTokenName("token");
        tokenService.setClientId("resourceClient");
        tokenService.setClientSecret("resourceSecret");
        return tokenService;
    }

}
