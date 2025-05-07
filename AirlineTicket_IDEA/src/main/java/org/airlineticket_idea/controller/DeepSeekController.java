package org.airlineticket_idea.controller;

import org.airlineticket_idea.service.DeepSeekService;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/deepseek")
@CrossOrigin
public class DeepSeekController {
    @Autowired
    private DeepSeekService deepSeekService;

    @RequestMapping("/ask")
    public Mono<Result<String>> askAI(@RequestBody String question) {

        System.out.println("接受到了AI请求，正在返回");
        System.out.println(question);
        return deepSeekService.getAIResponse(question);
    }
}
