package yyit.riceball.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import yyit.riceball.domain.RiceballOrder;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("riceballOrder")
public class OrderController {

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid RiceballOrder order, Errors errors, SessionStatus sessionStatus) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("提交订单: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}