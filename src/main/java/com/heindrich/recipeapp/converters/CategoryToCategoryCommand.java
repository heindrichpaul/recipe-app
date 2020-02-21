package com.heindrich.recipeapp.converters;

import com.heindrich.recipeapp.commands.CategoryCommand;
import com.heindrich.recipeapp.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }
        return CategoryCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
    }
}
