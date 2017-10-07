package com.kicinger.spring.security.boot.app.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification


/**
 * Created by krzysztofkicinger on 07/10/2017.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminControllerTestSpec extends Specification {

    @Autowired
    MockMvc mockMvc;

    def "when nonauthenticated user accesses /admin should be redirected to login"() {

        given:
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin");


        when:
        ResultActions response = mockMvc.perform(requestBuilder);

        then:
        response.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"));
    }

    def "when nonauthenticated user accesses /admin should be redirected to login (with expect)"() {

        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"));


    }

    @WithMockUser(username = "user", roles = "USER")
    def "when authenticated user does not have ADMIN role then request should be rejected"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    def "when authenticated user have ADMIN role then should be able to access /admin page"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


}