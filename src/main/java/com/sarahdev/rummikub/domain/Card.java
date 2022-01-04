package com.sarahdev.rummikub.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity(name = "card")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "color", "numero", "batch" }))
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long card_id;

	@Column
	private int color;

	@Column
	private int numero;

	@Column
	private int batch;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "player_id")
	private Player player;

	public Card() {
	}

	public Card(int color, int numero, int batch) {
		this.color = color;
		this.numero = numero;
		this.batch = batch;
		player = null;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Card)) {
			return false;
		}
		return (card_id != null && card_id.equals(((Card) other).getCard_id()));
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
