package com.heindrich.recipeapp.services;

import com.heindrich.recipeapp.commands.IngredientCommand;
import com.heindrich.recipeapp.domain.Ingredient;
import com.heindrich.recipeapp.domain.Recipe;
import com.heindrich.recipeapp.repositories.RecipeRepository;
import com.heindrich.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final ConversionService conversionService;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(ConversionService conversionService, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.conversionService = conversionService;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .map(ingredient -> conversionService.convert(ingredient, IngredientCommand.class)).findFirst();

            if (!ingredientCommandOptional.isPresent()) {
                log.error("Ingredient id not found: " + ingredientId);
                return new IngredientCommand();
            } else {
                return ingredientCommandOptional.get();
            }
        } else {
            log.error("recipe id not found. Id: " + recipeId);
            return new IngredientCommand();

        }
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(conversionService.convert(command, Ingredient.class));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            Optional<Ingredient> ingredientOptional1 = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();
            if (ingredientOptional1.isPresent()) {
                return conversionService.convert(ingredientOptional1.get(), IngredientCommand.class);
            } else {
                return new IngredientCommand();
            }
        } else {
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        }
    }
}
