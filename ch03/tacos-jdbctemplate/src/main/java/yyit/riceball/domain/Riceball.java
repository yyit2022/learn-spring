package yyit.riceball.domain;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 饭团
 * @author yyit
 */
@Data
public class Riceball {

    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min=5, message="名称必须至少有 5 个字符")
    private String name;


    @Size(min=1, message="您必须选择至少一种配料")
    private List<IngredientRef> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(new IngredientRef(ingredient.getId()));
    }


}
