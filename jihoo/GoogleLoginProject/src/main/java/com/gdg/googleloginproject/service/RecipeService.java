package com.gdg.googleloginproject.service;

import com.gdg.googleloginproject.domain.Recipe;
import com.gdg.googleloginproject.domain.User;
import com.gdg.googleloginproject.dto.request.RecipeRequestDto;
import com.gdg.googleloginproject.dto.response.RecipeInfoDto;
import com.gdg.googleloginproject.exception.CustomException;
import com.gdg.googleloginproject.exception.ErrorMessage;
import com.gdg.googleloginproject.repository.RecipeRepository;
import com.gdg.googleloginproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadRootPath;

    @Transactional
    public void saveRecipe(Principal principal, String uploadFilePath, RecipeRequestDto recipeRequestDto) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_IS_NOT_EXIST));

        recipeRepository.save(Recipe.builder()
                .title(recipeRequestDto.title())
                .description(recipeRequestDto.description())
                .imageUrl(uploadFilePath)
                .user(user)
                .build());
    }

    @Transactional(readOnly = true)
    public RecipeInfoDto readRecipe(Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorMessage.RECIPE_NOT_FOUND));

        return RecipeInfoDto.from(recipe);
    }

    @Transactional
    public RecipeInfoDto updateRecipe(Long recipeId, RecipeRequestDto recipeRequestDto, Principal principal, String uploadFilePath) {
        Long userId = Long.parseLong(principal.getName());

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorMessage.RECIPE_NOT_FOUND));

        if(recipe.getUser().getId() != userId){
            throw new CustomException(ErrorMessage.NO_PERMISSION_TO_EDIT);
        }

        recipe.update(recipeRequestDto, uploadFilePath);

        return RecipeInfoDto.from(recipe);
    }

    @Transactional
    public void deleteRecipe(Long recipeId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorMessage.RECIPE_NOT_FOUND));

        if(recipe.getUser().getId() != userId){
            throw new CustomException(ErrorMessage.NO_PERMISSION_TO_DELETE);
        }

        recipeRepository.delete(recipe);
    }

    //사진 파일 저장하고 경로 리턴할 메서드
    public String uploadImage(MultipartFile imageFile) throws IOException {

        File rootDir = new File(uploadRootPath);
        if (!rootDir.exists()) rootDir.mkdir();

        //이름 충돌 가능성 배제하기
        String uniqueFileName = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();

        //파일 저장하기
        File uploadFile = new File(uploadRootPath + "/" + uniqueFileName);
        imageFile.transferTo(uploadFile);

        return uniqueFileName;
    }

    public List<RecipeInfoDto> readAllRecipes(Principal principal) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        List<RecipeInfoDto> RecipeInfoDtos = allRecipes.stream().map(RecipeInfoDto::from).toList();

        return RecipeInfoDtos;
    }
}
