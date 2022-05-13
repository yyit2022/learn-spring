package yyit.riceball.data;

import yyit.riceball.domain.Ingredient;

import java.util.Optional;

public interface  IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

}
