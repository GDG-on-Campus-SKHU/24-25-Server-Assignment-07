package com.gdg.googleloginproject.controller;

import com.gdg.googleloginproject.domain.Recipe;
import com.gdg.googleloginproject.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<?> saveRecipe(RecipeRegisterDto recipeRegisterDto) {

    }

}
