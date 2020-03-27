package com.heindrich.recipeapp.controllers;

import com.heindrich.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
}
