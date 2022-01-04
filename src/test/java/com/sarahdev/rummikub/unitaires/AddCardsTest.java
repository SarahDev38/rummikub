package com.sarahdev.rummikub.unitaires;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sarahdev.rummikub.RummikubApplication;
import com.sarahdev.rummikub.domain.Card;
import com.sarahdev.rummikub.domain.Player;
import com.sarahdev.rummikub.repository.PlayerRepository;

@SpringBootTest(classes = RummikubApplication.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AddCardsTest {

	@Autowired
	private PlayerRepository playerRepository;

	@Test
	@DisplayName("Ajouter 1 carte à un joueur")
	public void test_Save1Card() {
		// Arrange
		final Player player = new Player("gersende");
		final Card card = new Card(2, 9, 1);

		// Act
		player.addCard(card);
		final Player savedPlayer = playerRepository.save(player);

		// Assert
		assertThat(savedPlayer.getCards().size()).isEqualTo(1);
		assertThat(savedPlayer.getCards().iterator().next().getColor()).isEqualTo(2);
		assertThat(savedPlayer.getCards().iterator().next().getNumero()).isEqualTo(9);
		assertThat(savedPlayer.getCards().iterator().next().getBatch()).isEqualTo(1);
	}
}
