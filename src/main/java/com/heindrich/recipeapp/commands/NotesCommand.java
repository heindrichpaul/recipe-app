package com.heindrich.recipeapp.commands;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class NotesCommand {
    private Long id;
    private String recipeNotes;
}
