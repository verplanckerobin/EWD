package com.springBoot.bibliotheek;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import domain.Auteur;
import domain.Boek;
import domain.Locatie;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.LocatieRepository;

public class AdminControllerMockTest {

    @Mock
    private BoekRepository boekRepoMock;

    @Mock
    private AuteurRepository auteurRepoMock;

    @Mock
    private LocatieRepository locatieRepoMock;

    @Mock
    private MessageSource messageSource;

    private AdminController controller;
    private MockMvc mockMvc;

    private Auteur auteur;
    private Locatie locatie;
    private Boek boek;

    @BeforeEach
    public void before() {
	MockitoAnnotations.openMocks(this);
	controller = new AdminController();
	mockMvc = standaloneSetup(controller).build();
	ReflectionTestUtils.setField(controller, "boekRepo", boekRepoMock);
	ReflectionTestUtils.setField(controller, "auteurRepo", auteurRepoMock);
	ReflectionTestUtils.setField(controller, "locatieRepo", locatieRepoMock);
	ReflectionTestUtils.setField(controller, "messageSource", messageSource);

	Authentication auth = Mockito.mock(Authentication.class);
	SecurityContext securityContext = Mockito.mock(SecurityContext.class);
	Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
	SecurityContextHolder.setContext(securityContext);

	auteur = new Auteur("TestNaam", "TestVoornaam");
	locatie = new Locatie(50, 100, "Veldegem");
	boek = new Boek("TestBoek", "9780596520687", BigDecimal.valueOf(10.00));
	boek.voegAuteurToe(auteur);
	boek.voegLocatieToe(locatie);
    }

    // Auteur

    @Test
    public void testVoegAuteurToeCorrect() throws Exception {
	mockMvc.perform(post("/voeg-auteur-toe").flashAttr("auteur", auteur))
		.andExpect(view().name("redirect:/voeg-boek-toe"));
    }

    @Test
    public void testVoegAuteurToeNaamNull() throws Exception {
	//@formatter:off
	auteur.setAuteurNaam(null);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-auteur-toe").flashAttr("auteur", auteur).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("auteur", "auteurNaam"))
		.andExpect(view().name("voegBoekToe"));
	//@formatter:on
    }

    @Test
    public void testVoegAuteurToeVoornaamNull() throws Exception {
	//@formatter:off
	auteur.setVoornaam(null);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-auteur-toe").flashAttr("auteur", auteur).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("auteur", "voornaam"))
		.andExpect(view().name("voegBoekToe"));
	//@formatter:on
    }

    @Test
    public void testVoegAuteurToeAuteurBestaat() throws Exception {
	Auteur bestaandeAuteur = new Auteur("TestNaam", "TestVoornaam");
	Mockito.when(auteurRepoMock.findByAuteurNaamAndVoornaam(bestaandeAuteur.getAuteurNaam(),
		bestaandeAuteur.getVoornaam())).thenReturn(bestaandeAuteur);
	BindingResult result = Mockito.mock(BindingResult.class);
	MvcResult mvcResult = mockMvc.perform(
		post("/voeg-auteur-toe").flashAttr("auteur", bestaandeAuteur).flashAttr("bindingResult", result))
		.andExpect(status().isOk()).andExpect(view().name("voegBoekToe")).andReturn();

	ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

	assertThat(modelMap).containsKey("errorAuteurBestaat");
    }

    // Locatie

    @Test
    public void testVoegLocatieToeCorrect() throws Exception {
	mockMvc.perform(post("/voeg-locatie-toe").flashAttr("locatie", locatie))
		.andExpect(view().name("redirect:/voeg-boek-toe"));
    }

    @Test
    public void testVoegLocatieToePlaatscode1TeKlein() throws Exception {
	locatie.setPlaatscode1(0);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-locatie-toe").flashAttr("locatie", locatie).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("locatie", "plaatscode1"))
		.andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegLocatieToePlaatscode2TeKlein() throws Exception {
	locatie.setPlaatscode2(0);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-locatie-toe").flashAttr("locatie", locatie).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("locatie", "plaatscode2"))
		.andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegLocatieToePlaatsNaamNull() throws Exception {
	locatie.setPlaatsnaam(null);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-locatie-toe").flashAttr("locatie", locatie).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("locatie", "plaatsnaam"))
		.andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegLocatieToePlaatsNaamCijfers() throws Exception {
	locatie.setPlaatsnaam("123456abcdef");
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-locatie-toe").flashAttr("locatie", locatie).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("locatie", "plaatsnaam"))
		.andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegLocatieToeVerschilTeKlein() throws Exception {
	locatie.setPlaatscode1(50);
	locatie.setPlaatscode2(60);
	BindingResult result = Mockito.mock(BindingResult.class);
	MvcResult mvcResult = mockMvc
		.perform(post("/voeg-locatie-toe").flashAttr("locatie", locatie).flashAttr("bindingResult", result))
		.andExpect(status().isOk()).andExpect(view().name("voegBoekToe")).andReturn();

	ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

	assertThat(modelMap).containsKey("errorVerschilCodes");
    }

    @Test
    public void testVoegLocatieBestaandeLocatie() throws Exception {
	Locatie bestaandeLocatie = new Locatie(50, 100, "Veldegem");
	Mockito.when(locatieRepoMock.findByPlaatscode1AndPlaatscode2AndPlaatsnaam(bestaandeLocatie.getPlaatscode1(),
		bestaandeLocatie.getPlaatscode2(), bestaandeLocatie.getPlaatsnaam())).thenReturn(bestaandeLocatie);

	BindingResult result = Mockito.mock(BindingResult.class);
	MvcResult mvcResult = mockMvc.perform(
		post("/voeg-locatie-toe").flashAttr("locatie", bestaandeLocatie).flashAttr("bindingResult", result))
		.andExpect(status().isOk()).andExpect(view().name("voegBoekToe")).andReturn();

	ModelMap modelMap = mvcResult.getModelAndView().getModelMap();

	assertThat(modelMap).containsKey("errorLocatieBestaat");
    }

    // Boek

    @Test
    public void testVoegBoekToeCorrect() throws Exception {
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek)).andExpect(view().name("redirect:/bibliotheek"));
    }

    @Test
    public void testVoegBoekToeGeenAuteurs() throws Exception {
	boek.setAuteurs(null);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "auteurs")).andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegBoekToeTeVeelAuteurs() throws Exception {
	Auteur auteur2 = new Auteur("A2", "A2");
	Auteur auteur3 = new Auteur("A3", "A3");
	Auteur auteur4 = new Auteur("A4", "A4");
	boek.voegAuteurToe(auteur2);
	boek.voegAuteurToe(auteur3);
	boek.voegAuteurToe(auteur4);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "auteurs")).andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegBoekToeGeenLocaties() throws Exception {
	boek.setLocaties(null);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "locaties")).andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegBoekToeTeVeelLocaties() throws Exception {
	Locatie locatie2 = new Locatie(52, 102, "Veldegem");
	Locatie locatie3 = new Locatie(53, 103, "Veldegem");
	Locatie locatie4 = new Locatie(54, 104, "Veldegem");
	boek.voegLocatieToe(locatie2);
	boek.voegLocatieToe(locatie3);
	boek.voegLocatieToe(locatie4);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "locaties")).andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegBoekToeNaamNull() throws Exception {
	boek.setNaam(null);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "naam")).andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegBoekToeIsbnNull() throws Exception {
	boek.setIsbnNummer(null);
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "isbnNummer")).andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegBoekToeIsbnTeKort() throws Exception {
	boek.setIsbnNummer("123456");
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "isbnNummer")).andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegBoekToeIsbnOngeldig() throws Exception {
	boek.setIsbnNummer("978-0-596-52068-6");
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "isbnNummer")).andExpect(view().name("voegBoekToe"));
    }

    @Test
    public void testVoegBoekToeAankoopprijsNul() throws Exception {
	boek.setAankoopprijs(BigDecimal.valueOf(0.0));
	BindingResult result = Mockito.mock(BindingResult.class);
	mockMvc.perform(post("/voeg-boek-toe").flashAttr("boek", boek).flashAttr("bindingResult", result))
		.andExpect(model().attributeHasFieldErrors("boek", "aankoopprijs"))
		.andExpect(view().name("voegBoekToe"));
    }
}
