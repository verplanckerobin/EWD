package com.springBoot.beer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.ColorBean;
import domain.ExpertBean;

@Controller
@RequestMapping("/beer")
public class BeerController {

    @Autowired
    private ExpertBean expertBean;

    @GetMapping
    public String showHomePage(Model model) {
	model.addAttribute("beerCommand", new BeerCommand());
	return "formView";
    }

    @PostMapping
    public String onSubmit(BeerCommand beerCommand, Model model) {
	model.addAttribute("listBeer", expertBean.getExpert(beerCommand.getColorSelected()));
	return "resultView";
    }

    @ModelAttribute("colorsList")
    public List<String> populateColors() {
	return (new ColorBean()).getColorsList();
    }
}
