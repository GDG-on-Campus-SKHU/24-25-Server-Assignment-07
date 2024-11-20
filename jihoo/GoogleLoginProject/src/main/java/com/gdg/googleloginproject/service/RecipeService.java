package com.gdg.googleloginproject.service;

import com.gdg.googleloginproject.domain.Recipe;
import com.gdg.googleloginproject.domain.User;
import com.gdg.googleloginproject.dto.request.RecipeRequestDto;
import com.gdg.googleloginproject.dto.response.RecipeInfoDto;
import com.gdg.googleloginproject.repository.RecipeRepository;
import com.gdg.googleloginproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadRootPath;

    public void saveRecipe(Principal principal, String uploadFilePath, RecipeRequestDto recipeRequestDto) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        recipeRepository.save(Recipe.builder()
                .title(recipeRequestDto.title())
                .description(recipeRequestDto.description())
                .imageUrl(uploadFilePath)
                .user(user)
                .build());
    }

    public RecipeInfoDto readRecipe(Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("레시피를 찾을 수 없습니다."));

        return RecipeInfoDto.from(recipe);
    }

    public RecipeInfoDto updateRecipe(Long recipeId, RecipeRequestDto recipeRequestDto, Principal principal, String uploadFilePath) {
        Long userId = Long.parseLong(principal.getName());

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("레시피를 찾을 수 없습니다."));

        if(recipe.getUser().getId() != userId){
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        recipe.update(recipeRequestDto, uploadFilePath);

        return RecipeInfoDto.from(recipe);
    }

    public void deleteRecipe(Long recipeId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("레시피를 찾을 수 없습니다."));

        if(recipe.getUser().getId() != userId){
            throw new RuntimeException("삭제 권한이 없습니다.");
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
}
