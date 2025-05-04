package org.airlineticket_idea;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.airlineticket_idea.mapper")
public class AirlineticketIdeaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineticketIdeaApplication.class, args);
	}

}
