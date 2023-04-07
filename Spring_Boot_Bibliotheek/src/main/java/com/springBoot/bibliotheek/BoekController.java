package com.springBoot.bibliotheek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.BoekRepository;

@Controller
@RequestMapping("/boek")
public class BoekController {

    @Autowired
    private BoekRepository boekRepo;

}
