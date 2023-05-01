package com.springBoot.bibliotheek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import domain.Auteur;
import domain.Authorities;
import domain.Boek;
import domain.Gebruiker;
import domain.Locatie;
import repository.BoekRepository;
import repository.GebruikerRepository;

public class FavorietenControllerMockTest {

    @Mock
    private BoekRepository boekRepoMock;

    @Mock
    private GebruikerRepository gebruikerRepoMock;

    private FavorietenController controller;
    private MockMvc mockMvc;

    private Principal principal;
    private Gebruiker gebruiker;
    private Boek boek;
    private Auteur auteur;
    private Locatie locatie;

    @BeforeEach
    public void before() {
	MockitoAnnotations.openMocks(this);
	controller = new FavorietenController();
	mockMvc = standaloneSetup(controller).build();
	ReflectionTestUtils.setField(controller, "boekRepo", boekRepoMock);
	ReflectionTestUtils.setField(controller, "gebruikerRepo", gebruikerRepoMock);

	this.principal = Mockito.mock(Principal.class);
	Mockito.when(principal.getName()).thenReturn("testUser");

	this.gebruiker = new Gebruiker("testUser", "123456", Authorities.ROLE_USER, true, 1);
	Mockito.when(gebruikerRepoMock.getGebruikerByUsername("testUser")).thenReturn(gebruiker);

	this.boek = new Boek("TestBoek", "9783161484100", BigDecimal.valueOf(10.00));
	Mockito.when(boekRepoMock.findById(1L)).thenReturn(Optional.of(boek));

	this.auteur = new Auteur("AuteurVoornaam", "AuteurAchternaam");
	boek.voegAuteurToe(auteur);

	this.locatie = new Locatie(50, 100, "TestPlaats");
	boek.voegLocatieToe(locatie);
    }

    @Test
    public void testGetBoekDetailPage() throws Exception {
	//@formatter:off
	mockMvc.perform(get("/boek/{id}", 1).principal(principal))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("boek"))
		.andExpect(model().attributeExists("lijstAuteurs"))
		.andExpect(model().attributeExists("lijstLocaties"))
		.andExpect(model().attributeExists("isReedsFavoriet"))
		.andExpect(model().attribute("boek", Matchers.equalTo(boek)))
		.andExpect(model().attribute("isReedsFavoriet", false))
		.andExpect(model().attribute("lijstAuteurs", Matchers.equalTo(boek.getAuteurs())))
		.andExpect(model().attribute("lijstLocaties", Matchers.equalTo(boek.getLocaties())))
		.andExpect(view().name("boek-detail"));
	//@formatter:on
    }

    @Test
    public void testGetBoekReedsFavorietAndMaxAantalBereikt() throws Exception {
	gebruiker.addFavoriet(boek);
	//@formatter:off
	mockMvc.perform(get("/boek/{id}", 1).principal(principal))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("boek"))
		.andExpect(model().attributeExists("isReedsFavoriet"))
		.andExpect(model().attributeExists("maxAantal"))
		.andExpect(model().attribute("boek", Matchers.equalTo(boek)))
		.andExpect(model().attribute("isReedsFavoriet", true))
		.andExpect(model().attributeExists("maxAantal"))
		.andExpect(view().name("boek-detail"));
	//@formatter:on
    }

    @Test
    public void testAddToFavoriet() throws Exception {
	mockMvc.perform(post("/boek/{id}", 1).principal(principal)).andExpect(view().name("redirect:/bibliotheek"))
		.andExpect(flash().attribute("favorietBericht", boek.getNaam() + " werd toegevoegd aan favorieten"));
    }

    @Test
    public void testRemoveFromFavoriet() throws Exception {
	gebruiker.addFavoriet(boek);
	mockMvc.perform(post("/boek/{id}", 1).principal(principal)).andExpect(view().name("redirect:/bibliotheek"))
		.andExpect(flash().attribute("favorietBericht", boek.getNaam() + " werd verwijderd uit favorieten"));
    }
}
