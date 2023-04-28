package com.springBoot.bibliotheek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

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

    private AdminController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
	MockitoAnnotations.openMocks(this);
	controller = new AdminController();
	mockMvc = standaloneSetup(controller).build();
	ReflectionTestUtils.setField(controller, "boekRepo", boekRepoMock);
	ReflectionTestUtils.setField(controller, "auteurRepo", auteurRepoMock);
	ReflectionTestUtils.setField(controller, "locatieRepo", locatieRepoMock);
    }

    @Test
    public void testAccess() throws Exception {
	mockMvc.perform(get("/voeg-boek-toe")).andExpect(status().isOk());
    }
}
