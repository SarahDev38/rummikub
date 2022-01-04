package com.sarahdev.rummikub.acceptance;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = { "pretty",
		"html:target/cucumber" }, monochrome = true, extraGlue = "com.sarahdev.rummikub.acceptance.steps")
public class CucumberAIT {

}
