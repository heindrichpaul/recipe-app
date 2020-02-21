package com.heindrich.recipeapp.converters;

import com.heindrich.recipeapp.commands.NotesCommand;
import com.heindrich.recipeapp.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return null;
        }
        return Notes.builder()
                .id(source.getId())
                .recipeNotes(source.getRecipeNotes())
                .build();
    }
}
