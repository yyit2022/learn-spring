package com.yyit.ch12.sample01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 此处为功能说明
 * </p>
 *
 * @author yanpingping
 * @date 2022/5/12
 **/
@RestController
public class HomeController {

    @GetMapping("/")
    public Mono<String> home(){
        return Mono.just("hello Spring webflux!");
    }

}
