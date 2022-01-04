package com.sarahdev.rummikub.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sarahdev.rummikub.controller.GameController;

import lombok.Data;

@Data
@Component
public class PlayersForm {
	private List<String> names = new ArrayList<>();

	public PlayersForm() {
		for (int i = 0; i < GameController.MAX_PLAYERS; i++) {
			names.add("");
		}
	}

}