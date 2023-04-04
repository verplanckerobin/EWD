package com.springBoot.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.BankCustomer;
import domain.BankCustomerLookup;

@Controller
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankCustomerLookup bankCustomerLookup;

    @GetMapping
    public String showFormPage(Model model) {
	model.addAttribute("bankCustomer", new BankCustomer());
	return "form";
    }

    @PostMapping
    public String onSubmit(BankCustomer bankCustomer, Model model) {
	BankCustomer currentCustomer = bankCustomerLookup.getCustomer(bankCustomer.getId());
	if (currentCustomer == null) {
	    return "unknownCustomer";
	}
	model.addAttribute("customer", currentCustomer);
	return (currentCustomer.getBalance() < 0 ? "negativeBalance" : "balance");
    }
}
