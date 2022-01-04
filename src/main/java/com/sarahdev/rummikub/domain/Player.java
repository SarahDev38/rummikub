package com.sarahdev.rummikub.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity(name = "player")
@Slf4j
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long player_id;

	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "player", cascade = { CascadeType.ALL, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	private final Set<Card> cards;

	public Player() {
		name = "";
		cards = new HashSet<>();
	}

	public Player(String name) {
		this.name = name;
		cards = new HashSet<>();
	}

	public void addCard(Card card) {
		getCards().add(card);
		card.setPlayer(this);
	}

	public void addCards(Set<Card> cardsList) {
		getCards().addAll(cardsList);
		cardsList.forEach(card -> card.setPlayer(this));
	}

//	public void removeAllCards() {
//		log.info("call remove All Cards of player name " + name);
//		final Set<Card> removedCards = new HashSet<>(getCards());
//		removedCards.forEach(this::removeCard);
//	}
//
//	public void removeCard(Card card) {
//		log.info("call remove one card of id " + card.getCard_id() + " from player name " + name);
//		getCards().remove(card);
//		card.setPlayer(null);
//	}
}
