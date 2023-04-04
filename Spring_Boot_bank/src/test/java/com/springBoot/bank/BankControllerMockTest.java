package com.springBoot.bank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import domain.BankCustomer;
import domain.BankCustomerLookup;

public class BankControllerMockTest {

    @Mock
    private BankCustomerLookup mock;

    private BankController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
	MockitoAnnotations.openMocks(this);
	controller = new BankController();
	mockMvc = standaloneSetup(controller).build();
	ReflectionTestUtils.setField(controller, "bankCustomerLookup", mock);
    }

    @Test
    public void testBankPost_PositiveBalance() throws Exception {
	BankCustomer expResult = new BankCustomer("1", "Robin", "Verplancke", 5000);
	Mockito.when(mock.getCustomer("1")).thenReturn(expResult);

	mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer("1")))
		.andExpect(view().name("balance")).andExpect(model().attributeExists("customer"))
		.andExpect(model().attribute("customer", expResult));
    }

    @Test
    public void testBankPost_NegativeBalance() throws Exception {
	BankCustomer expResult = new BankCustomer("2", "Robin", "Verplancke", -2000);
	Mockito.when(mock.getCustomer("2")).thenReturn(expResult);

	mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer("2")))
		.andExpect(view().name("negativeBalance")).andExpect(model().attributeExists("customer"))
		.andExpect(model().attribute("customer", expResult));
    }

    @Test
    public void testBankPost_UnknownCustomer() throws Exception {
	Mockito.when(mock.getCustomer("3")).thenReturn(null);

	mockMvc.perform(post("/bank").flashAttr("bankCustomer", new BankCustomer("3")))
		.andExpect(view().name("unknownCustomer")).andExpect(model().attributeDoesNotExist("customer"));
    }

}
