package com.heindrich.recipeapp.services;

import com.heindrich.recipeapp.commands.RecipeCommand;
import com.heindrich.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long l);

    void deleteById(Long l);
}
