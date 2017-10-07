package com.kicinger.spring.security.boot.app.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;

/** Created by krzysztofkicinger on 07/10/2017. */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {

    @Autowired private TestRestTemplate restTemplate;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void whenUserAccessItsDataAndIsAuthorizedShouldGetThem() throws Exception {
        Principal principal = restTemplate.getForObject("/user/me", Principal.class);
        assertThat(principal).isNotNull();
    }
}
