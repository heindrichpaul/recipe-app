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
                Ingredient ingredient = conversionService.convert(command, Ingredient.class);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //todo check for fail
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();


            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredient -> recipeIngredient.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredient -> recipeIngredient.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredient -> recipeIngredient.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return conversionService.convert(savedIngredientOptional.orElse(new Ingredient()), IngredientCommand.class);

        } else {
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
            //todo return error if not found
        }
    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientToDelete);
                recipeRepository.save(recipe);
            } else {
                log.error("ingredient id:{} was not found", ingredientId);
            }
        } else {
            log.error("Recipe id: {} not found", recipeId);
        }
    }
}
