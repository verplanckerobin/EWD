package com.springBoot.bibliotheek;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
@SpringJUnitConfig
class BibliotheekControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void accessDeniedPageGet() throws Exception {
	mockMvc.perform(get("/403")).andExpect(status().isOk()).andExpect(view().name("403"));
    }

    @WithMockUser(username = "user", roles = { "USER", "ADMIN" })
    @Test
    public void testAccessWithUserRole() throws Exception {
	mockMvc.perform(get("/bibliotheek")).andExpect(status().isOk()).andExpect(view().name("bibliotheekOverzicht"))
		.andExpect(model().attributeExists("username")).andExpect(model().attributeExists("userRole"))
		.andExpect(model().attributeExists("boekList"));
    }

    @WithMockUser(username = "user", roles = { "NOT_USER" })
    @Test
    public void testNoAccess() throws Exception {
	mockMvc.perform(get("/bibliotheek")).andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", roles = { "USER" })
    @Test
    public void testNoAccessAdminConsole() throws Exception {
	mockMvc.perform(get("/voeg-boek-toe")).andExpect(status().isForbidden());
    }

    @Test
    void testWrongPassword() throws Exception {
	mockMvc.perform(formLogin("/login").user("username", "nameUser").password("password", "wrongpassword"))
		.andExpect(status().isFound()).andExpect(redirectedUrl("/login?error"));
    }

    @Test
    void testCorrectPassword() throws Exception {
	mockMvc.perform(formLogin("/login").user("username", "admin").password("password", "admin"))
		.andExpect(status().isFound()).andExpect(redirectedUrl("/bibliotheek"));
    }
}
