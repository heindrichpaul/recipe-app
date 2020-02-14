package com.heindrich.recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long expected = 4L;
        category.setId(expected);
        assertEquals(expected, category.getId());
    }

    @Test
    void getDescription() {
        String expected = "American";
        category.setDescription(expected);
        assertEquals(expected, category.getDescription());
    }

    @Test
    void getRecipes() {
    }
}