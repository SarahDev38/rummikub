package com.sarahdev.rummikub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sarahdev.rummikub.domain.Game;
import com.sarahdev.rummikub.domain.Player;
import com.sarahdev.rummikub.domain.PlayerException;
import com.sarahdev.rummikub.domain.PlayersForm;
import com.sarahdev.rummikub.service.ICardService;
import com.sarahdev.rummikub.service.IPlayerService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Validated
@Slf4j
public class GameController {
	public static final String INIT_TEMPLATE = "index";
	public static final String PLAY_TEMPLATE = "play";

	public static final int MAX_PLAYERS = 4;
	public static final int MIN_PLAYERS = 2;
	public static final int START_CARD_NUMBER = 14;

	private int playersCount = 2;
	private int currentPlayer = 0;

	private Game game;

	@Autowired
	private IPlayerService playerService;
	@Autowired
	private ICardService cardService;

	@GetMapping("/")
	public String home(Model model) {
		return "redirect:/players";
	}

	@GetMapping("/players")
	public String initGame(Model model) {
		final PlayersForm form = new PlayersForm();
		model.addAttribute("playersCount", playersCount);
		model.addAttribute("form", form);
		return INIT_TEMPLATE;
	}

	@RequestMapping(value = "/players", params = { "addPlayer" })
	public String addPlayer(@ModelAttribute("form") PlayersForm form, Model model) {
		model.addAttribute("playersCount", ++playersCount);
		model.addAttribute("form", form);
		return INIT_TEMPLATE;
	}

	@RequestMapping(value = "/players", params = { "supressPlayer" })
	public String removePlayer(@ModelAttribute("form") PlayersForm form, Model model) {
		model.addAttribute("playersCount", --playersCount);
		model.addAttribute("form", form);
		return INIT_TEMPLATE;
	}

	@PostMapping("/players")
	public String initGame(@ModelAttribute("form") PlayersForm form, Model model) {
		log.info("Controller : init game call ");
		game = new Game(form.getNames(), playersCount);
		playersCount = game.getNames().size();
		cardService.initCards();
		playerService.deleteAllPlayers();
		game.setStockCards(cardService.gatherAllShuffledCards());
		for (int index = 0; index < playersCount; index++) {
			final Player player = new Player(game.getNames().get(index));
			try {
				playerService.savePlayer(player);
			} catch (final PlayerException e) {
				log.info("Controller : duplicate name error ");
				model.addAttribute("playersCount", playersCount);
				model.addAttribute("form", form);
				model.addAttribute("message_erreur", e.getMessage());
				return INIT_TEMPLATE;
			}
			player.addCards(game.removeCards(START_CARD_NUMBER));
			playerService.updatePlayer(player);
		}
		game.setCurrentCards(cardService.findByPlayerName(game.getNames().get(0)));
		model.addAttribute("form", game);
		model.addAttribute("playersCount", playersCount);
		model.addAttribute("current", currentPlayer);
		model.addAttribute("cardsCount", game.getCurrentCards().size());
		return PLAY_TEMPLATE;
	}

	@PostMapping("/play")
	public String playGame(Model model) {
		model.addAttribute("playersCount", playersCount);
		if (playersCount > 1) {
			currentPlayer = (currentPlayer + 1) % playersCount;
		}
		game.setCurrentCards(cardService.findByPlayerName(game.getNames().get(currentPlayer)));
		model.addAttribute("form", game);
		model.addAttribute("current", currentPlayer);
		model.addAttribute("cardsCount", game.getCurrentCards().size());
		return PLAY_TEMPLATE;
	}

}
