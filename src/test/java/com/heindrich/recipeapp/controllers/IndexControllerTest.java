package com.heindrich.recipeapp.controllers;

import com.heindrich.recipeapp.domain.Recipe;
import com.heindrich.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {
    IndexController indexController;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndex() {
        //given
        String expected = "index";

        Set<Recipe> recipeHashSet = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipeHashSet.add(recipe);
        recipe = new Recipe();
        recipe.setId(2L);
        recipeHashSet.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipeHashSet);
        ArgumentCaptor<Set<Recipe>> argumentCaptur = ArgumentCaptor.forClass(Set.class);
        //when
        String actual = indexController.getIndex(model);

        //then
        assertEquals(expected, actual);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptur.capture());
        Set<Recipe> r = argumentCaptur.getValue();
        assertEquals(2, r.size());
    }

    @Test
    void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}