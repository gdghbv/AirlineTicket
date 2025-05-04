package org.airlineticket_idea.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    private String model = "deepseek-chat";
    private List<Message> messages;
    private double temperature = 0.7;
    private int max_tokens = 2048;  // 添加API可能需要的字段
    private boolean stream = false;

    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
