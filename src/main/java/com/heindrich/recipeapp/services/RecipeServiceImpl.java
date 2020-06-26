package com.heindrich.recipeapp.services;

import com.heindrich.recipeapp.commands.RecipeCommand;
import com.heindrich.recipeapp.domain.Recipe;
import com.heindrich.recipeapp.exceptions.NotFoundException;
import com.heindrich.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final ConversionService conversionService;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ConversionService conversionService) {
        this.recipeRepository = recipeRepository;
        this.conversionService = conversionService;
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
        if (!recipe.isPresent()) {
            throw new NotFoundException("Recipe Not Found");
        }
        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe recipe = conversionService.convert(command, Recipe.class);
        return conversionService.convert(recipeRepository.save(recipe), RecipeCommand.class);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l) {
        Optional<Recipe> recipe = recipeRepository.findById(l);
        return conversionService.convert(recipe.orElse(null), RecipeCommand.class);
    }

    @Override
    @Transactional
    public void deleteById(Long l) {
        recipeRepository.deleteById(l);
    }

}
