package yyit.riceball.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import yyit.riceball.data.IngredientRepository;
import yyit.riceball.domain.Ingredient;
import yyit.riceball.domain.Riceball;
import yyit.riceball.domain.RiceballOrder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 * @author yanpingping
 */

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("riceballOrder")
public class DesignRiceballController {

    private final IngredientRepository ingredientRepo;

    public DesignRiceballController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }


    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "riceballOrder")
    public RiceballOrder order() {
        return new RiceballOrder();
    }

    @ModelAttribute(name = "riceball")
    public Riceball riceball() {
        return new Riceball();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processRiceball(@Valid Riceball riceball, Errors errors,
                              @ModelAttribute RiceballOrder riceballOrder) {


        if (errors.hasErrors()) {
            return "design";
        }

        riceballOrder.addRiceBall(riceball);
        log.info("正在处理饭团: {}", riceball);

        return "redirect:/orders/current";
    }



    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Ingredient.Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }

}
