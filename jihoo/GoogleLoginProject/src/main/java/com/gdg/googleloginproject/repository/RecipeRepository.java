package com.gdg.googleloginproject.repository;

import com.gdg.googleloginproject.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
