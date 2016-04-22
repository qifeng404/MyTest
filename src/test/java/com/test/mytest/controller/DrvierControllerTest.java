package com.test.mytest.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.test.mytest.controller.DrvierController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class DrvierControllerTest {
	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new DrvierController()).build();
	}

	@Test
	public void getHello() throws Exception {
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/drvier").accept(MediaType.APPLICATION_JSON));
		actions.andExpect(MockMvcResultMatchers.status().isOk());
		actions.andExpect(MockMvcResultMatchers.content().string("{\"gid\":1,\"content\":\"Drvier, world\"}"));
	}
}
