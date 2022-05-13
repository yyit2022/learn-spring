package yyit.riceball.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import yyit.riceball.domain.Ingredient;
import yyit.riceball.domain.Riceball;
import yyit.riceball.domain.RiceballOrder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author yanpingping
 */

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("riceballOrder")
public class DesignRiceballController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients, type));
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



    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
