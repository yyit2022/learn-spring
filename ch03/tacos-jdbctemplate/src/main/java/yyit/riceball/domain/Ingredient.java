package yyit.riceball.domain;


import lombok.Data;

/**
 * 配料
 * @author yyit
 */
@Data
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
