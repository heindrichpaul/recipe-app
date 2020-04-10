package com.heindrich.recipeapp.configs;

import com.heindrich.recipeapp.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesToNotesCommand notesToNotesCommand;
    private final NotesCommandToNotes notesCommandToNotes;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public WebConfig(CategoryToCategoryCommand categoryToCategoryCommand, CategoryCommandToCategory categoryCommandToCategory, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, NotesToNotesCommand notesToNotesCommand, NotesCommandToNotes notesCommandToNotes, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand, UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesToNotesCommand = notesToNotesCommand;
        this.notesCommandToNotes = notesCommandToNotes;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(this.categoryToCategoryCommand);
        registry.addConverter(this.categoryCommandToCategory);
        registry.addConverter(this.ingredientToIngredientCommand);
        registry.addConverter(this.ingredientCommandToIngredient);
        registry.addConverter(this.notesToNotesCommand);
        registry.addConverter(this.notesCommandToNotes);
        registry.addConverter(this.recipeToRecipeCommand);
        registry.addConverter(this.recipeCommandToRecipe);
        registry.addConverter(this.unitOfMeasureToUnitOfMeasureCommand);
        registry.addConverter(this.unitOfMeasureCommandToUnitOfMeasure);
    }
}
