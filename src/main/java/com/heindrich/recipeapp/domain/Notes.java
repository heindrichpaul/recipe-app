package com.heindrich.recipeapp.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = "recipe")
@EqualsAndHashCode(exclude = "recipe")
@Entity
@Builder
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;
    @Lob//CLOB
    private String recipeNotes;

}
