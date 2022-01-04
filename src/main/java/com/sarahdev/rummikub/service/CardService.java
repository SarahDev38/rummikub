package com.sarahdev.rummikub.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarahdev.rummikub.domain.Card;
import com.sarahdev.rummikub.repository.CardRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class CardService implements ICardService {
//	private List<Card> stockCards;
	private static int MAX_COLOR = 4;
	private static int MAX_NUMERO = 13;

	@Autowired
	private CardRepository repository;

	public CardService(CardRepository cardRepository) {
		repository = cardRepository;
	}

	@Override
	public Set<Card> getAllCards() {
		log.info("call get all cards");
		final Set<Card> cards = new HashSet<>();
		repository.findAll().forEach(cards::add);
		return cards;
	}

	@Override
	public void deleteAllCards() {
		log.info("call delete all cards");
		repository.deleteAll();
	}

	@Override
	public Set<Card> findByColorAndNumeroAndBatch(int color, int numero, int batch) {
		return repository.findByColorAndNumeroAndBatch(color, numero, batch);
	}

	@Override
	public void initCards() {
		if (getAllCards().size() != (MAX_COLOR * MAX_NUMERO * 2)) {
			deleteAllCards();
			IntStream.range(0, MAX_COLOR).forEach(color -> {
				IntStream.range(0, MAX_NUMERO).forEach(numero -> {
					repository.save(new Card(color, numero, 0));
					repository.save(new Card(color, numero, 1));
				});
			});
		} else {
			repository.findAll().forEach(this::initPlayerCard);
		}
	}

	@Override
	public Card initPlayerCard(Card card) {
		log.info("call init card of color: " + card.getColor() + " numero: " + card.getNumero() + " batch: "
				+ card.getBatch());
		card.setPlayer(null);
		return repository.save(card);
	}

	@Override
	public Set<Card> findByPlayerName(String name) {
		return repository.findByPlayerName(name);
	}

	@Override
	public List<Card> gatherAllShuffledCards() {
		log.info("call init stock of cards ");
		final List<Card> stockCards = new ArrayList<Card>();
		repository.findAll().forEach(stockCards::add);
		shuffleStockCards(stockCards);
		return stockCards;
	}

	private void shuffleStockCards(List<Card> stockCards) {
		final Random random = new Random();
		IntStream.range(0, stockCards.size())
				.forEach(i -> Collections.swap(stockCards, i, random.nextInt(stockCards.size())));
	}

}