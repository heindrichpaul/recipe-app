package com.heindrich.recipeapp.commands;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CategoryCommand {
    private Long id;
    private String description;
}
