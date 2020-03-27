package com.heindrich.recipeapp.services;

import com.heindrich.recipeapp.commands.RecipeCommand;
import com.heindrich.recipeapp.converters.RecipeCommandToRecipe;
import com.heindrich.recipeapp.converters.RecipeToRecipeCommand;
import com.heindrich.recipeapp.domain.Recipe;
import com.heindrich.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I am in the service");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> recipe = recipeRepository.findById(l);
        return recipe.orElse(null);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe recipe = recipeCommandToRecipe.convert(command);
        return recipeToRecipeCommand.convert(recipeRepository.save(recipe));
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l) {
        Optional<Recipe> recipe = recipeRepository.findById(l);
        return recipeToRecipeCommand.convert(recipe.orElse(null));
    }

    @Override
    @Transactional
    public void deleteById(Long l) {
        recipeRepository.deleteById(l);
    }

}
