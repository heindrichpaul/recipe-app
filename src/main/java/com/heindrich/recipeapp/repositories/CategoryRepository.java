package com.heindrich.recipeapp.repositories;

import com.heindrich.recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
