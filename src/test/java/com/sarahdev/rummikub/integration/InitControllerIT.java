package com.sarahdev.rummikub.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sarahdev.rummikub.controller.GameController;
import com.sarahdev.rummikub.domain.Card;
import com.sarahdev.rummikub.domain.Player;
import com.sarahdev.rummikub.repository.CardRepository;
import com.sarahdev.rummikub.repository.PlayerRepository;
import com.sarahdev.rummikub.service.ICardService;
import com.sarahdev.rummikub.service.IPlayerService;

@WebMvcTest(controllers = { GameController.class, IPlayerService.class, ICardService.class })
@ExtendWith(SpringExtension.class)
public class InitControllerIT {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlayerRepository playerRepositoryMock;
	@MockBean
	private CardRepository cardRepositoryMock;

	private static List<Card> stock;
	private Player player;

	@BeforeAll
	public static void initStockCards() {
		stock = new ArrayList<>();
		IntStream.range(0, 4).forEach(color -> {
			IntStream.range(0, 13).forEach(numero -> {
				stock.add(new Card(color, numero, 0));
				stock.add(new Card(color, numero, 1));
			});
		});
	}

	@Test
	public void whenEnter_RedirectTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.redirectedUrl("/players"))
				.andExpect(MockMvcResultMatchers.status().isFound());
	}

	@Test
	public void EnterPlayersPageTest() throws Exception {
		final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/players"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
		assertThat(result.getResponse().getContentAsString()).contains("démarrer");

	}

	@Test
	public void givenAUser_whenHisNameIsEntered_thenHisNameIsRegisteredAnd14CardsAreShown() throws Exception {
		// GIVEN
		player = new Player("gersende");
		IntStream.range(0, 14).forEach(i -> player.addCard(stock.get(i)));
		when(playerRepositoryMock.save(new Player("gersende"))).thenReturn(player);
		when(cardRepositoryMock.findByPlayerName("gersende")).thenReturn(player.getCards());
		when(cardRepositoryMock.findAll()).thenReturn(stock);
		// WHEN
		final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/players").param("names[0]", "gersende"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
		// THEN
		verify(playerRepositoryMock, atLeast(2)).save(any(Player.class));
		assertThat(result.getResponse().getContentAsString()).contains("gersende");
		assertThat(result.getResponse().getContentAsString()).contains("14");
	}

	@Test
	public void whenAddPlayersIsAsked_NewRowAddedInPlayersPageTest() throws Exception {
		final MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/players").param("names[0]", "gersende").param("addPlayer", "addPlayer"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
		assertThat(result.getResponse().getContentAsString()).contains(">2</span>");
	}

	@Test
	public void whenSuppressPlayersIsAsked_NewRowSuppressedInPlayersPageTest() throws Exception {
		final MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/players").param("names[0]", "gersende")
						.param("names[1]", "sarah").param("supressPlayer", "supressPlayer"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
		assertThat(result.getResponse().getContentAsString()).contains(">1</span>");
	}

}
