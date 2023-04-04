package com.springBoot.beer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import domain.ExpertBean;

public class BeerControllerMockTest {

    @Mock
    private ExpertBean mock;

    private BeerController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
	MockitoAnnotations.openMocks(this);
	controller = new BeerController();
	mockMvc = standaloneSetup(controller).build();
	ReflectionTestUtils.setField(controller, "expertBean", mock);
    }

    @Test
    public void testPostRequest() throws Exception {
	List<String> expResult = List.of("TEST");
	Mockito.when(mock.getExpert("light")).thenReturn(expResult);

	// mockMvc.perform(post("/beer").param("colorSelected", "light"))
	mockMvc.perform(post("/beer").flashAttr("beerCommand", new BeerCommand("light")))
		.andExpect(view().name("resultView")).andExpect(model().attributeExists("listBeer"))
		.andExpect(model().attribute("listBeer", expResult));
    }

}
