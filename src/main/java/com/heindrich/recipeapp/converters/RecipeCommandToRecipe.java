package com.heindrich.recipeapp.converters;

import com.heindrich.recipeapp.commands.RecipeCommand;
import com.heindrich.recipeapp.domain.Category;
import com.heindrich.recipeapp.domain.Ingredient;
import com.heindrich.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        Set<Category> categories = new HashSet<>();
        source.getCategories()
                .forEach(c -> categories.add(categoryConverter.convert(c)));

        Set<Ingredient> ingredients = new HashSet<>();
        source.getIngredients()
                .forEach(i -> ingredients.add(ingredientConverter.convert(i)));


        return Recipe.builder()
                .categories(categories)
                .cookTime(source.getCookTime())
                .description(source.getDescription())
                .difficulty(source.getDifficulty())
                .directions(source.getDirections())
                .id(source.getId())
                .ingredients(ingredients)
                .notes(notesConverter.convert(source.getNotes()))
                .prepTime(source.getPrepTime())
                .servings(source.getServings())
                .source(source.getSource())
                .url(source.getUrl())
                .build();
    }
}
