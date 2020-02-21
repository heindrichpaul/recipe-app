package com.heindrich.recipeapp.converters;

import com.heindrich.recipeapp.commands.IngredientCommand;
import com.heindrich.recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }
        return IngredientCommand.builder()
                .amount(source.getAmount())
                .description(source.getDescription())
                .id(source.getId())
                .unitOfMeasure(unitOfMeasureConverter.convert(source.getUnitOfMeasure()))
                .build();
    }
}
