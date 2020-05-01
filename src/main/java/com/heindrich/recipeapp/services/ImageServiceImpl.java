package com.heindrich.recipeapp.services;

import com.heindrich.recipeapp.domain.Recipe;
import com.heindrich.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Optional<Recipe> recipe = recipeRepository.findById(recipeId);

            Byte[] bytesObjects = new Byte[file.getBytes().length];
            for (int i = 0; i < file.getBytes().length; i++) {
                bytesObjects[i] = file.getBytes()[i];
            }

            recipe.ifPresent(r -> {
                r.setImage(bytesObjects);
                recipeRepository.save(r);
            });

            log.debug("Received a file");
        } catch (IOException e) {
            log.error("Error occurred", e);
            e.printStackTrace();
        }

    }
}
