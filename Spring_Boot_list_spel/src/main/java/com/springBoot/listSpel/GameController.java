package com.springBoot.listSpel;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import domain.Game;

@Controller
@RequestMapping("/game")
@SessionAttributes("game")
public class GameController {

    @GetMapping
    public String initGame(Model model) {
        model.addAttribute("game", new Game());
        return "startGame";
    }
    
    @PostMapping
    public String onSubmit(@Valid Game game, BindingResult result) {
        if (result.hasErrors()) {
            return "startGame";
        }
        game.startGame();
        return "game";
    }
       
    @GetMapping(value = "/{index}")
    public String show(@PathVariable Integer index, Game game, Model model) {
    	game.play(index);

        if (game.isWin()) 
            model.addAttribute("gameStatus", "win");
        else if (game.isLost()) 
            model.addAttribute("gameStatus", "lose");

        return "game";
    }

}