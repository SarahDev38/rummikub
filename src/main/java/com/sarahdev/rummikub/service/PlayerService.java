package com.sarahdev.rummikub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarahdev.rummikub.configuration.CustomProperties;
import com.sarahdev.rummikub.domain.Player;
import com.sarahdev.rummikub.domain.PlayerException;
import com.sarahdev.rummikub.repository.PlayerRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class PlayerService implements IPlayerService {
	@Autowired
	CustomProperties properties;

	@Autowired
	private PlayerRepository repository;

	public PlayerService(PlayerRepository playerRepository) {
		repository = playerRepository;
	}

	@Override
	public Player savePlayer(Player player) throws PlayerException {
		log.info("call save player of name " + player.getName());
		try {
			return repository.save(player);
		} catch (final Exception e) {
			throw new PlayerException(properties.getPlayer_name_duplicated());
		}
	}

	@Override
	public Player updatePlayer(Player player) {
		log.info("call update player of name " + player.getName());
		return repository.save(player);
	}

	@Override
	public void deleteAllPlayers() {
		log.info("call delete all players");
		repository.deleteAll();
	}

	@Override
	public void deletePlayer(final Long id) {
		log.info("call delete player for id " + id);
		repository.deleteById(id);
	}

	@Override
	public Player getPlayerByName(String name) {
		log.info("call get player by name = " + name);
		return repository.findByName(name);
	}

}