package com.heindrich.recipeapp.converters;

import com.heindrich.recipeapp.commands.NotesCommand;
import com.heindrich.recipeapp.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }
        return NotesCommand.builder()
                .id(source.getId())
                .recipeNotes(source.getRecipeNotes())
                .build();
    }
}
