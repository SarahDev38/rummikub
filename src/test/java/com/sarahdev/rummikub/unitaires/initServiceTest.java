package com.sarahdev.rummikub.unitaires;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sarahdev.rummikub.domain.Player;
import com.sarahdev.rummikub.domain.PlayerException;
import com.sarahdev.rummikub.repository.PlayerRepository;
import com.sarahdev.rummikub.service.IPlayerService;
import com.sarahdev.rummikub.service.PlayerService;

@ExtendWith(MockitoExtension.class)
public class initServiceTest {
	@Mock
	PlayerRepository playerRepositoryMock;

	private IPlayerService serviceUT;

	@BeforeEach
	public void init() {
		serviceUT = new PlayerService(playerRepositoryMock);
	}

	@Test
	@DisplayName("service communique avec PlayerRepository pour ajouter un joueur")
	public void initGameService_shouldCallRepository_forSavePlayer() throws PlayerException {
		// GIVEN
		final Player player = new Player("sarah");
		when(playerRepositoryMock.save(player)).thenReturn(player);
		// WHEN
		final Player resultPlayer = serviceUT.savePlayer(player);
		// THEN
		verify(playerRepositoryMock).save(player);
		assertThat(resultPlayer.getName()).isEqualTo("sarah");
	}

	@Test
	@DisplayName("service communique avec PlayerRepository pour effacer un joueur")
	public void initGameService_shouldCallRepository_forDeletePlayer() throws PlayerException {
		// WHEN
		serviceUT.deletePlayer(1L);
		// THEN
		verify(playerRepositoryMock).deleteById(1L);
	}

	@Test
	@DisplayName("service renvoie une exception si nom des joueurs dupliqués")
	public void initGameService_shouldThrowDuplicatePlayerException_foDuplicatedPlayersName() {
		// GIVEN
		final Player player = new Player("sarah");
		when(playerRepositoryMock.save(player)).thenReturn(player).thenThrow(RuntimeException.class);
		// WHEN
		assertThrows(RuntimeException.class, () -> {
			serviceUT.savePlayer(player);
			serviceUT.savePlayer(player);
		});
		// THEN
		verify(playerRepositoryMock, times(2)).save(new Player("sarah"));
	}

}
