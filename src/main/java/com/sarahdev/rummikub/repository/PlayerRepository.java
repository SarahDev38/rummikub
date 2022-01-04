package com.sarahdev.rummikub.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sarahdev.rummikub.domain.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

	Player findByName(String name);

}
