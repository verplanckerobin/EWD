package com.springBoot.bank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import domain.BankCustomer;

@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetRequest() throws Exception {
	mockMvc.perform(get("/bank")).andExpect(view().name("form")).andExpect(status().isOk())
		.andExpect(model().attributeExists("bankCustomer"));
    }

    @Test
    public void testBankPost_PositiveBalance() throws Exception {
	mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer("123"))).andExpect(status().isOk())
		.andExpect(view().name("balance")).andExpect(model().attributeExists("customer"));
    }

    @Test
    public void testBankPost_NegativeBalance() throws Exception {
	mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer("789"))).andExpect(status().isOk())
		.andExpect(view().name("negativeBalance")).andExpect(model().attributeExists("customer"));
    }

    @Test
    public void testBankPost_UnknownCustomer() throws Exception {
	mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer("111"))).andExpect(status().isOk())
		.andExpect(view().name("unknownCustomer")).andExpect(model().attributeDoesNotExist("customer"));
    }
}
