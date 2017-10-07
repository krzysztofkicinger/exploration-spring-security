package com.kicinger.spring.security.boot.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/** Created by krzysztofkicinger on 07/10/2017. */
@RestController
public class UserRestController {

    @RequestMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
