package com.example.demo.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests unitaires pour FirstController
 *
 * WebMvcTest charge uniquement la couche MVC (contrôleurs)
 * sans démarrer le serveur complet, ce qui rend les tests rapides
 */
@WebMvcTest(FirstController.class)
public class FirstControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test 1 : Vérifier que la page d'accueil retourne un statut HTTP 200 (OK)
     */
    @Test
    public void testHomeReturnsStatus200() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk());
    }

    /**
     * Test 2 : Vérifier que le framework passé en paramètre apparaît dans la page
     */
    @Test
    public void testHomeContainsFramework() throws Exception {
        mockMvc.perform(get("/?framework=Spring"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Spring")));
    }

    /**
     * Test 3 : Vérifier que le framework par défaut (Spring Boot) apparaît
     * quand aucun paramètre n'est fourni
     */
    @Test
    public void testHomeDefaultFramework() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Spring Boot")));
    }

    /**
     * Test 4 : Vérifier que la version par défaut (v3.3.3) apparaît
     */
    @Test
    public void testHomeDefaultVersion() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("v3.3.3")));
    }

    /**
     * Test 5 : Vérifier que la vue correcte est retournée
     */
    @Test
    public void testHomeReturnsCorrectView() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("pages/home"));
    }

    /**
     * Test 6 : Vérifier qu'on peut passer une version personnalisée
     */
    @Test
    public void testHomeWithCustomVersion() throws Exception {
        mockMvc.perform(get("/?version=v4.0.0"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("v4.0.0")));
    }

    /**
     * TEST VOLONTAIREMENT EN ÉCHEC
     * Test 7 : Ce test échoue car la page ne contient pas "Symfony"
     * Purpose : Démontrer que les tests vérifient réellement le comportement
     */
    @Test
    public void testHomeContainsSymfony() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Symfony")));
    }
}


