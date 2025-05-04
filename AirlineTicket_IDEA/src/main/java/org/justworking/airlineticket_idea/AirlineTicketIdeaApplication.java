package org.justworking.airlineticket_idea;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.justworking.airlineticket_idea.mapper")
public class AirlineTicketIdeaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlineTicketIdeaApplication.class, args);
    }

}
