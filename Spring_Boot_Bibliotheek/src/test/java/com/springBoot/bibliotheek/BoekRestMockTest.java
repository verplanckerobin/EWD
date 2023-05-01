package com.springBoot.bibliotheek;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import domain.Boek;
import exceptions.AuteurNotFoundException;
import exceptions.BoekNotFoundException;
import service.BoekService;

@SpringBootTest
public class BoekRestMockTest {

    @Mock
    private BoekService mock;

    private BoekRestController controller;
    private MockMvc mockMvc;

    private final String ISBN = "9780261104013";
    private final String AUTEURNAAM = "Rowling";

    @BeforeEach
    public void before() {
	MockitoAnnotations.openMocks(this);
	controller = new BoekRestController();
	mockMvc = standaloneSetup(controller).build();
	ReflectionTestUtils.setField(controller, "boekService", mock);

    }

    private void performRest(String uri) throws Exception {
	mockMvc.perform(get(uri)).andExpect(status().isOk()).andExpect(jsonPath("$.isbnNummer").value(ISBN));
    }

    private Boek aBoek(String isbn) {
	Boek boek = new Boek("TestBoek", isbn, BigDecimal.valueOf(10.00));
	return boek;
    }

    @Test
    public void testGetBoekByIsbn_isOk() throws Exception {
	Mockito.when(mock.getBoekByISBN(ISBN)).thenReturn(aBoek(ISBN));
	performRest("/rest/boek/isbn/" + ISBN);
	Mockito.verify(mock).getBoekByISBN(ISBN);
    }

    @Test
    public void testGetBoekenByAuteur_isOk() throws Exception {
	List<Boek> lijstBoeken = new ArrayList<>();
	Boek boek1 = new Boek("TestBoek1", "9780345339713", BigDecimal.valueOf(10.00));
	Boek boek2 = new Boek("TestBoek2", "9780553808049", BigDecimal.valueOf(10.00));
	lijstBoeken.add(boek1);
	lijstBoeken.add(boek2);

	Mockito.when(mock.getBoekenByAuteur(AUTEURNAAM)).thenReturn(lijstBoeken);
	mockMvc.perform(get("/rest/boek/auteur/" + AUTEURNAAM)).andExpect(status().isOk());
	Mockito.verify(mock).getBoekenByAuteur(AUTEURNAAM);
    }

    @Test
    public void testGetBoekByIsbn_notFound() throws Exception {
	Mockito.when(mock.getBoekByISBN(ISBN)).thenThrow(new BoekNotFoundException(ISBN));
	Exception exception = assertThrows(Exception.class, () -> {
	    mockMvc.perform(get("/rest/boek/isbn/" + ISBN)).andReturn();
	});
	assertTrue(exception.getCause() instanceof BoekNotFoundException);
	Mockito.verify(mock).getBoekByISBN(ISBN);
    }

    @Test
    public void testGetBoekenByAuteur_notFound() throws Exception {
	Mockito.when(mock.getBoekenByAuteur(AUTEURNAAM)).thenThrow(new AuteurNotFoundException(ISBN));
	Exception exception = assertThrows(Exception.class, () -> {
	    mockMvc.perform(get("/rest/boek/auteur/" + AUTEURNAAM)).andReturn();
	});
	assertTrue(exception.getCause() instanceof AuteurNotFoundException);
	Mockito.verify(mock).getBoekenByAuteur(AUTEURNAAM);
    }
}
