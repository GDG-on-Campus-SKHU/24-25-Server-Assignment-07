package com.gdg.googleloginproject.controller;

import com.gdg.googleloginproject.dto.request.RecipeRequestDto;
import com.gdg.googleloginproject.dto.response.RecipeInfoDto;
import com.gdg.googleloginproject.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeInfoDto> saveRecipe(Principal principal,
                                        @RequestPart(name = "ImageFile", required = false) MultipartFile imageFile,
                                                    @RequestPart(name = "recipe") RecipeRequestDto recipeRequestDto) throws IOException {

        //이미지 파일 경로
        String uploadFilePath = getUploadFilePath(imageFile);

        recipeService.saveRecipe(principal, uploadFilePath, recipeRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeInfoDto> readRecipe(@PathVariable Long recipeId, Principal principal) {
        return ResponseEntity.ok().body(recipeService.readRecipe(recipeId));
    }

    @PatchMapping("/{recipeId}")
    public ResponseEntity<RecipeInfoDto> updateRecipe(@PathVariable Long recipeId
            , @RequestPart(name = "recipe") RecipeRequestDto recipeRequestDto, Principal principal,
                                                      @RequestPart(name = "ImageFile", required = false) MultipartFile imageFile) throws IOException {

        String uploadFilePath = getUploadFilePath(imageFile);

        return ResponseEntity.ok().body(recipeService.updateRecipe(recipeId, recipeRequestDto, principal, uploadFilePath));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long recipeId, Principal principal) {
        recipeService.deleteRecipe(recipeId, principal);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<RecipeInfoDto>> readAllRecipes(Principal principal) {
        return ResponseEntity.ok().body(recipeService.readAllRecipes(principal));
    }

    //파일 경로를 리턴할 메서드 추출
    private String getUploadFilePath(MultipartFile imageFile) throws IOException {
        String uploadFilePath = null;

        if (imageFile != null) {
            uploadFilePath = recipeService.uploadImage(imageFile);
        }
        return uploadFilePath;
    }
}
