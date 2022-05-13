package yyit.riceball.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yyit
 */
@Controller
public class HomeController {

    /**
     * 首页
     * @return
     */
    @GetMapping("/")
    public String home(){
        return "home";
    }

}
