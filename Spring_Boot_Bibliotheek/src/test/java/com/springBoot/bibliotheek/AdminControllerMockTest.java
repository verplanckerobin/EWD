package com.springBoot.bibliotheek;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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
    }

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
}
