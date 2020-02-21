package com.heindrich.recipeapp.converters;

import com.heindrich.recipeapp.commands.UnitOfMeasureCommand;
import com.heindrich.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Nullable
    @Synchronized
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }
        return UnitOfMeasure.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
    }
}
