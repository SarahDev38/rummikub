package com.sarahdev.rummikub.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
public class InitGameStep {
	@Autowired
	MockMvc mockMvc;

	private String _name1;

	@Given("un utilisateur veut jouer")
	public void a_user_wants_to_play() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@When("il saisit son nom {string}")
	public void game_starts(String name1) throws Exception {
		_name1 = name1;
	}

	@Then("un joueur porte le nom {string} et détient {int} cartes")
	public void the_student_is_shown(String name1, Integer cardsCount) throws Exception {
		final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/players").param("names[0]", _name1))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
		assertThat(result.getResponse().getContentAsString()).contains(">" + name1 + "</span");
		assertThat(result.getResponse().getContentAsString()).contains("<span>14</");
	}
}
