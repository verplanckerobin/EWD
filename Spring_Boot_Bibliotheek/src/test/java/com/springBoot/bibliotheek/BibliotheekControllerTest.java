package com.springBoot.bibliotheek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BibliotheekControllerTest {

    @Autowired
    private MockMvc mockMvc;

 //@formatter:off
    @Test
    public void testGetRequest() throws Exception {
	mockMvc.perform(get("/bibliotheek")).andExpect(view().name("bibliotheek")).andExpect(status().isOk())
		.andExpect(model().attributeExists("username"))
		.andExpect(model().attributeExists("userListRoles"))
		.andExpect(model().attributeExists("boekList"));
    }

//@formatter:on
}
