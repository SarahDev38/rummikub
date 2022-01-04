package com.sarahdev.rummikub.service;

import java.util.List;
import java.util.Set;

import com.sarahdev.rummikub.domain.Card;

public interface ICardService {

	Set<Card> getAllCards();

	void deleteAllCards();

	Set<Card> findByColorAndNumeroAndBatch(int color, int numero, int batch);

	Card initPlayerCard(Card card);

	List<Card> gatherAllShuffledCards();

	Set<Card> findByPlayerName(String name);

	void initCards();

}
