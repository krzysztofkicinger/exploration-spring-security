package com.kicinger.spring.security.boot.app

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Created by krzysztofkicinger on 07/10/2017.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
        features = arrayOf("src/test/resources/features"),
        glue = arrayOf("com.kicinger.spring.security.boot.app.steps"),
        format = arrayOf("pretty")
)
class BehavioralTests {

}