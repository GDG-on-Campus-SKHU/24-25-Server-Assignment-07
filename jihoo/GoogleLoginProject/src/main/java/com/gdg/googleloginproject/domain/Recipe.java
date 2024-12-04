package com.gdg.googleloginproject.domain;

import com.gdg.googleloginproject.dto.request.RecipeRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Recipe(String title, String description, String imageUrl, User user) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public void update(RecipeRequestDto recipeRequestDto, String uploadFilePath) {
        this.title = recipeRequestDto.title();
        this.description = recipeRequestDto.description();
        this.imageUrl = uploadFilePath;
    }
}
