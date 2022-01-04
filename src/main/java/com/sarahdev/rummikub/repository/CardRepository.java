package com.sarahdev.rummikub.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sarahdev.rummikub.domain.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

	Set<Card> findByColorAndNumeroAndBatch(int color, int numero, int batch);

	Set<Card> findByPlayerName(String name);

}
