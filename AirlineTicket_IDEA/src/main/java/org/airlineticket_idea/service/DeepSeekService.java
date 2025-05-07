package org.airlineticket_idea.service;

import org.airlineticket_idea.utils.Result;
import reactor.core.publisher.Mono;

public interface DeepSeekService {
    Mono<Result<String>> getAIResponse(String question);
}
