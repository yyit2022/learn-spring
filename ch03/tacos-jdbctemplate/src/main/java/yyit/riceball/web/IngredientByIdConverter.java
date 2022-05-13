package yyit.riceball.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import yyit.riceball.data.IngredientRepository;
import yyit.riceball.domain.Ingredient;

import java.util.HashMap;
import java.util.Map;
import yyit.riceball.domain.Ingredient.Type;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {


    private IngredientRepository ingredientRepo;




    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}
