package com.kicinger.spring.security.oauth.controllers;

import com.kicinger.spring.security.oauth.model.Foo;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @PreAuthorize("#oauth.hasScope('read')")
    @GetMapping("/foos/{id}")
    public Foo findById(@PathVariable long id) {
        final String name = new RandomStringGenerator.Builder().build().generate(4);
        return new Foo(id, name);
    }

}
