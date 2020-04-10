package com.heindrich.recipeapp.services;

import com.heindrich.recipeapp.commands.IngredientCommand;
import com.heindrich.recipeapp.converters.IngredientToIngredientCommand;
import com.heindrich.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.heindrich.recipeapp.domain.Ingredient;
import com.heindrich.recipeapp.domain.Recipe;
import com.heindrich.recipeapp.repositories.RecipeRepository;
import com.heindrich.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @BeforeEach
    public void setUp() throws Exception {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(conversionService,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndRecipeIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(conversionService.convert(any(Ingredient.class), any())).thenReturn(ingredientToIngredientCommand.convert(ingredient3));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }


    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Ingredient ingredient = Ingredient.builder().id(3L).build();
        Recipe savedRecipe = Recipe.builder().id(2L).ingredients(new HashSet<>()).build();
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        when(conversionService.convert(any(IngredientCommand.class), any())).thenReturn(ingredient);
        when(conversionService.convert(any(Ingredient.class), any())).thenReturn(ingredientToIngredientCommand.convert(ingredient));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(conversionService, times(1)).convert(any(Ingredient.class), any());
        verify(conversionService, times(1)).convert(any(IngredientCommand.class), any());

    }
}