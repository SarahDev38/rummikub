package com.sarahdev.rummikub.unitaires;

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
import com.sarahdev.rummikub.repository.PlayerRepository;

@SpringBootTest(classes = RummikubApplication.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AddPlayerTest {

	@Autowired
	private PlayerRepository playerRepository;

	@BeforeEach
	public void initPlayersDataBase() {
		playerRepository.deleteAll();
	}

	@Test
	@DisplayName("Ajouter 1 joueur au jeu")
	public void test_Save1Player() {
		// Arrange
		final Player player = new Player("gersende");

		// Act
		final Player savedPlayer = playerRepository.save(player);

		// Assert
		assertThat(savedPlayer.getName()).contains("gersende");
	}

	@Test
	@DisplayName("Ajouter 1 joueur au nom vide")
	public void test_Add1Player_withEmptyName() {
		// Arrange
		final Player player = new Player("");

		// Act
		final Player savedPlayer = playerRepository.save(player);

		// Assert
		assertThat(savedPlayer.getName()).contains("");
	}
}
