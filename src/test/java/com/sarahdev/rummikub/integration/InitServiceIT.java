package com.sarahdev.rummikub.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sarahdev.rummikub.RummikubApplication;
import com.sarahdev.rummikub.domain.Player;
import com.sarahdev.rummikub.domain.PlayerException;
import com.sarahdev.rummikub.repository.PlayerRepository;
import com.sarahdev.rummikub.service.IPlayerService;
import com.sarahdev.rummikub.service.PlayerService;

@SpringBootTest(classes = RummikubApplication.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class InitServiceIT {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private final IPlayerService serviceUT = new PlayerService(playerRepository);

	@BeforeEach
	public void initPlayersDataBase() {
		playerRepository.deleteAll();
	}

	@Test
	@DisplayName("service ajoute un joueur à partir de son nom")
	public void service_shouldAddAPlayer_whenGivenAName() throws PlayerException {
		// GIVEN
		final Player player = new Player("sarah");

		// WHEN
		final Player resultPlayer = serviceUT.savePlayer(player);

		// THEN
		assertThat(resultPlayer.getName()).isEqualTo("sarah");
	}

	@Test
	@DisplayName("service récupère le joueur enregistré sous un nom donné")
	public void service_shouldGetAPlayerCards_whenGivenAName() throws PlayerException {
		// GIVEN
		final String name = "sarah";
		serviceUT.savePlayer(new Player(name));

		// WHEN
		final Player resultPlayer = serviceUT.getPlayerByName(name);

		// THEN
		assertThat(resultPlayer.getName()).isEqualTo("sarah");
		assertThat(resultPlayer.getPlayer_id()).isNotEqualTo(0L);
	}

}
