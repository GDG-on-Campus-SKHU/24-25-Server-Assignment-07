package com.gdg.googleloginproject.dto.response;

import com.gdg.googleloginproject.domain.Recipe;
import lombok.Builder;

@Builder
public record RecipeInfoDto(
        Long id,
        String title,
        String description,
        String imageUrl,
        String username
) {
    public static RecipeInfoDto from(Recipe recipe) {
        return RecipeInfoDto.builder()
                .id(recipe.getId())
                .description(recipe.getDescription())
                .title(recipe.getTitle())
                .imageUrl(recipe.getImageUrl())
                .username(recipe.getUser().getUsername())
                .build();
    }
}
