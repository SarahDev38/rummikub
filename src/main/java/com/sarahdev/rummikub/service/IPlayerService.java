package com.sarahdev.rummikub.service;

import com.sarahdev.rummikub.domain.Player;
import com.sarahdev.rummikub.domain.PlayerException;

public interface IPlayerService {

	void deletePlayer(final Long id);

	void deleteAllPlayers();

	Player savePlayer(Player player) throws PlayerException;

	Player updatePlayer(Player player);

	Player getPlayerByName(String name);
}
