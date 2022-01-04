package com.sarahdev.rummikub.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.sarahdev.rummikub.controller.GameController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Component
@Slf4j
public class Game {
	private List<String> names = new ArrayList<>();

	private final List<int[]> currentCards = new ArrayList<>(); // [0] : color ; [1] : numero

	private List<Card> stockCards;

	// plateau de jeu
	private Boolean[][] sets = new Boolean[8][13];
	private Boolean[][] sequences = new Boolean[13][8];

	public Game() {
		for (int i = 0; i < GameController.MAX_PLAYERS; i++) {
			names.add("");
		}
	}

	public Game(List<String> names, int playersCount) {
		this.names = new ArrayList<>();
		IntStream.range(0, playersCount).forEach(index -> {
			if (!names.get(index).isEmpty()) {
				this.names.add(names.get(index));
			}
		});
	}

	public void setCurrentCards(Set<Card> cardsSet) {
		currentCards.clear();
		final List<Card> orderedCards = new ArrayList<>();
		orderedCards.addAll(cardsSet);
		Collections.sort(orderedCards, new Comparator<Card>() {
			@Override
			public final int compare(Card c1, Card c2) {
				if (c1.getNumero() != c2.getNumero()) {
					return c1.getNumero() - c2.getNumero();
				}
				return c1.getColor() - c2.getColor();
			}
		});
		orderedCards.forEach(card -> currentCards.add(new int[] { card.getColor(), card.getNumero() }));
	}

	public Set<Card> removeCards(int quantity) {
		log.info("call remove " + quantity + " cards from stock end of call");
		final Set<Card> removedCards = new HashSet<>();
		if (quantity == 0) {
			return null;
		}
		IntStream.range(0, quantity).forEach(i -> {
			final Card card = stockCards.remove(0);
			removedCards.add(card);
		});
		return removedCards;
	}

}