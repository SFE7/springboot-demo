package com.example.demo.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIndexReturnsStatus200() throws Exception {
        mockMvc.perform(get("/person/index"))
            .andExpect(status().isOk())
            .andExpect(view().name("pages/person/index"));
    }

    @Test
    public void testIndexContainsWelcomeMessage() throws Exception {
        mockMvc.perform(get("/person/index"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Bienvenue")));
    }

    @Test
    public void testPersonListReturnsStatus200() throws Exception {
        mockMvc.perform(get("/person/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("pages/person/personList"));
    }

    @Test
    public void testPersonListContainsInitialPersons() throws Exception {
        mockMvc.perform(get("/person/list"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Einstein")))
            .andExpect(content().string(containsString("Curie")))
            .andExpect(content().string(containsString("Hawking")));
    }

    @Test
    public void testPersonListContainsActionLinks() throws Exception {
        mockMvc.perform(get("/person/list"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Modifier")))
            .andExpect(content().string(containsString("Supprimer")));
    }

    @Test
    public void testAddPersonFormReturnsStatus200() throws Exception {
        mockMvc.perform(get("/person/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("pages/person/addPerson"));
    }

    @Test
    public void testAddPersonWithValidData() throws Exception {
        mockMvc.perform(post("/person/add")
                .param("firstName", "Isaac")
                .param("lastName", "Newton"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/list"));
    }

    @Test
    public void testAddPersonWithEmptyData() throws Exception {
        mockMvc.perform(post("/person/add")
                .param("firstName", "")
                .param("lastName", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("pages/person/addPerson"));
    }

    @Test
    public void testEditPersonFormReturnsStatus200() throws Exception {
        mockMvc.perform(get("/person/edit/0"))
            .andExpect(status().isOk())
            .andExpect(view().name("pages/person/editPerson"));
    }

    @Test
    public void testEditPersonFormContainsPersonData() throws Exception {
        mockMvc.perform(get("/person/edit/0"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Albert")))
            .andExpect(content().string(containsString("Einstein")));
    }

    @Test
    public void testEditPersonWithInvalidIndex() throws Exception {
        mockMvc.perform(get("/person/edit/999"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/list"));
    }

    public void testUpdatePersonWithValidData() throws Exception {
        mockMvc.perform(post("/person/update")
                .param("index", "0")
                .param("firstName", "Albertus")
                .param("lastName", "Einstein"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/list"));
    }

    @Test
    public void testDeletePersonWithValidIndex() throws Exception {
        mockMvc.perform(get("/person/delete/0"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/list"));
    }

    @Test
    public void testDeletePersonWithInvalidIndex() throws Exception {
        mockMvc.perform(get("/person/delete/999"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/list"));
    }

    @Test
    public void testDeletePersonWithNegativeIndex() throws Exception {
        mockMvc.perform(get("/person/delete/-1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/list"));
    }
}


