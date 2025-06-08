package org.airlineticket_idea.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.airlineticket_idea.mapper.AirlineMapper;
import org.airlineticket_idea.pojo.Airline;
import org.airlineticket_idea.pojo.ChatRequest;
import org.airlineticket_idea.pojo.ChatResponse;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.service.DeepSeekService;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DeepSeekServiceImpl implements DeepSeekService {
    @Autowired
    private AirlineMapper airlineMapper;
    private WebClient webClient;
    public DeepSeekServiceImpl(@Value("${deepseek.api.key}") String apiKey, WebClient.Builder webClientBuilder) {
        //构造函数设置请求头发送请求
        this.webClient = webClientBuilder
                .baseUrl("https://api.deepseek.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

    }
    @Override
    public Mono<Result<String>> getAIResponse(String question) {
        QueryWrapper<Airline> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper.eq("a.date", LocalDate.now())
                .gt("a.departure_time", LocalTime.now().plusHours(3))
                .or()
                .gt("a.date", LocalDate.now())
        );
        //通过提示词工程prompt来自定义系统
        List<AirlineVO> tickets = airlineMapper.selectAirlineListWithAirport(queryWrapper);
        StringBuilder data = new StringBuilder();
        for (AirlineVO ticket : tickets) {  //给ai发送数据库的机票信息
            data.append(String.format(
                    "航班id：%s 起始地：%s 目的地：%s 飞机id: %s 出发日期：%s 出发时间：%s 到达时间：%s 起始机场：%s 目的机场：%s 起始机场位置：" +
                            "%s 目的机场位置：%s 起始机场电话：%s 目的机场电话：%s 航班头等舱数量: %s 航班商务舱数量: %s 航班经济舱数量: %s 航行时间：%s 基本价格：%s\n"
                    ,ticket.getAirlineId(),ticket.getDeparture(),ticket.getArrival(),ticket.getAirplaneId(),ticket.getDate(),ticket.getDepartureTime(),ticket.getArrivalTime(),
                    ticket.getDepartureAirportName(),ticket.getArrivalAirportName(),ticket.getDepartureAirportAddress(),ticket.getArrivalAirportAddress()
                    ,ticket.getDepartureAirportPhone(),ticket.getArrivalAirportPhone(),ticket.getFirstSeat(),ticket.getSecondSeat(),ticket.getThirdSeat(),ticket.getDuration(),ticket.getPrice()
            ));
        }

        ChatRequest request = new ChatRequest();
        String systemPrompt = """
        你是订票系统网站的专属 AI 助手，仅回答与 **飞机订票、购票、旅游出行** 和 **你自己** 相关的问题。
        如果用户咨询你和机票相关的问题，则你必须要结合数据库进行回答，你要 **结合数据库里面的机票的信息** 来回答用户的问题，这是机票的数据，其中头等舱是基本价格的1.5倍，商务舱是价格的1.2倍，经济舱是价格的1倍""" + data + """
        如果用户的问题与我要求的或者出行方案无关，请提示你的身份。
        """;
     //设置向ai发送对话的请求，第一个Message是系统提示，第二个Message是用户的问题
        request.setMessages(List.of(
                new ChatRequest.Message("system", systemPrompt),
                new ChatRequest.Message("user", question)
        ));
    //发送对话
        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        code -> code == HttpStatus.UNAUTHORIZED,
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("认证失败: " + errorBody)))
                )
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API错误: " + errorBody)))
                )
                .bodyToMono(ChatResponse.class)
                .map(response -> response.getChoices().get(0).getMessage().getContent())
                .map(Result::ok) // 将 String 包装成 Result
                .onErrorResume(e -> Mono.just(Result.build(null, 500, e.getMessage()))); // 错误处理
    }
}
