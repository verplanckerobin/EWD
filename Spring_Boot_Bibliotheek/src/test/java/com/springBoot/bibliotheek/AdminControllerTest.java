package com.springBoot.bibliotheek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

  //@formatter:off
    @WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
    @Test
    public void testGetRequestAsAdmin() throws Exception {
	mockMvc.perform(get("/voeg-boek-toe")).andExpect(view().name("voegBoekToe")).andExpect(status().isOk())
		.andExpect(model().attributeExists("userRole"))
		.andExpect(model().attributeExists("boek"))
		.andExpect(model().attributeExists("auteur"))
		.andExpect(model().attributeExists("locatie"))
		.andExpect(model().attributeExists("auteurs"))
		.andExpect(model().attributeExists("locaties"));
    }
    //@formatter:on

    @WithMockUser(username = "user", roles = { "USER" })
    @Test
    public void testNoAccessAsUser() throws Exception {
	mockMvc.perform(get("/voeg-boek-toe")).andExpect(status().isForbidden());
    }
}
