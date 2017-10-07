package com.kicinger.spring.security.boot.app.steps

import com.kicinger.spring.security.boot.app.SpringSecurityBootApplication
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Static methods from SecurityMockMvcRequestPostProcessors:
 *  - user(String)
 *  - roles(String...)
 *
 * Static methods from MockMvcResultMatchers:
 *  - status()
 *  - redirectedUrlPattern()
 *
 * Static methods from MockMvcRequestBuilders:
 *  - get()
 *
 */
@ContextConfiguration(classes = arrayOf(SpringSecurityBootApplication::class))
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class AdminControllerSteps(
    var requestBuilder: MockHttpServletRequestBuilder? = null,
    var user: UserRequestPostProcessor? = null
) {

    @Autowired
    var mockMvc: MockMvc? = null

    @Given("^user has username '(.*)' and role '(.*)'$")
    fun givenUser(username: String, role: String) {
        user = user(username).roles(role)
    }

    @When("^user wants to access '(.*)' page$")
    fun whenUserWantsToAccess(page: String) {
        requestBuilder = get(page)
    }

    @And("^user authenticates with its credentials$")
    fun andUserAuthenticatesWithItsCredentials() {
        requestBuilder!!.with(user!!)
    }

    @Then("^the request should be redirected to '(.*)'$")
    fun thenTheRequestShouldBeRedirected(path: String) {
        mockMvc!!.perform(requestBuilder!!)
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrlPattern("**$path"))
    }

    @Then("^admin page should be displayed$")
    fun thenAdminPageShouldBeDisplayed() {
        mockMvc!!.perform(requestBuilder!!)
                .andExpect(status().is2xxSuccessful)
    }

    @Then("^request should be rejected$")
    fun thenRequestShouldBeRejected() {
        mockMvc!!.perform(requestBuilder!!)
                .andExpect(status().is4xxClientError)
    }


}