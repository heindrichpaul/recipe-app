package com.heindrich.recipeapp.converters;

import com.heindrich.recipeapp.commands.IngredientCommand;
import com.heindrich.recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }
        return Ingredient.builder()
                .amount(source.getAmount())
                .description(source.getDescription())
                .id(source.getId())
                .unitOfMeasure(unitOfMeasureConverter.convert(source.getUnitOfMeasure()))
                .build();
    }
}
