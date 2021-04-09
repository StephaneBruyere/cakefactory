package com.factory.cake.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@SpringBootTest
@AutoConfigureMockMvc
class IndexControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebClient webClient;

	@Test
	@DisplayName("index page returns the landing page")
	void returnsLandingPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Cake Factory")));
	}

	@Test
	void checkOnItems() throws Exception {
		HtmlPage page = webClient.getPage("/");
		assertTrue("Cake Factory".equals(page.getTitleText()));
		assertTrue((page.asNormalizedText().contains("a delicious apple pie with fresh apples")));
		assertTrue((page.asNormalizedText().contains("Ice cream and melting chocolate : the perfect match")));
	}
	
	@Test
	@DisplayName("index page return a list of items from the database")
	void returnsListOfItemsFromDb() throws Exception {
		final String expectedName = "Profiteroles";
		HtmlPage page = webClient.getPage("/");
		assertThat(page.querySelectorAll(".item-name")).anyMatch(domElement -> expectedName.equals(domElement.asNormalizedText()));
	}

}
