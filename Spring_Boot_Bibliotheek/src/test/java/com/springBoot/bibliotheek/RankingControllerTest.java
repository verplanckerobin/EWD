package com.springBoot.bibliotheek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
public class RankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

  //@formatter:off
    @WithMockUser(username = "user", roles = { "USER", "ADMIN" })
    @Test
    public void testGetRequest() throws Exception {
	mockMvc.perform(get("/populairste-boeken")).andExpect(view().name("populairste-boeken")).andExpect(status().isOk())
		.andExpect(model().attributeExists("userRole"))
		.andExpect(model().attributeExists("lijstPopulairsteBoeken"));
    }
    //@formatter:on

}
