package com.heindrich.recipeapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int prepTime;
    private int cookTime;
    private int serving;
    private String source;
    private String url;
    @Lob//CLOB
    private String directions;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
    @Lob//BLOB
    private byte[] image;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public Recipe addIngredients(Ingredient... ingredients) {
        for (Ingredient ingredient : ingredients) {
            ingredient.setRecipe(this);
            this.ingredients.add(ingredient);
        }

        return this;
    }
}
