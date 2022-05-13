package yyit.riceball.domain;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 饭团
 * @author yyit
 */
@Data
public class Riceball {

    @NotNull
    @Size(min=5, message="名称必须至少有 5 个字符")
    private String name;

    @NotNull
    @Size(min=1, message="您必须选择至少一种配料")
    private List<Ingredient> ingredients;
}
