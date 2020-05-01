package com.heindrich.recipeapp.converters;

import com.heindrich.recipeapp.commands.CategoryCommand;
import com.heindrich.recipeapp.commands.IngredientCommand;
import com.heindrich.recipeapp.commands.RecipeCommand;
import com.heindrich.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Override
    @Nullable
    @Synchronized
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        Set<CategoryCommand> categories = new HashSet<>();
        source.getCategories()
                .forEach(c -> categories.add(categoryConverter.convert(c)));

        Set<IngredientCommand> ingredients = new HashSet<>();
        source.getIngredients()
                .forEach(i -> ingredients.add(ingredientConverter.convert(i)));


        return RecipeCommand.builder()
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
