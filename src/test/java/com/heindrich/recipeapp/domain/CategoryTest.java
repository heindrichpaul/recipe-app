package com.heindrich.recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;
    private static final Long id = 4L;
    private static final String description = "American";
    private static final HashSet<Recipe> recipes = new HashSet<>();

    @BeforeEach
    public void setUp() {
        recipes.add(Recipe.builder()
                .id(12L)
                .build());
        category = Category.builder()
                .id(id)
                .description(description)
                .recipes(recipes)
                .build();
    }

    @Test
    void getId() {
        assertEquals(id, category.getId());
    }

    @Test
    void getDescription() {
        assertEquals(description, category.getDescription());
    }

    @Test
    void getRecipes() {
        assertEquals(1, category.getRecipes().size());
    }
}