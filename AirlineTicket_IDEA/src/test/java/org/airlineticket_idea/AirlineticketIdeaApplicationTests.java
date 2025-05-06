package org.airlineticket_idea;

import org.airlineticket_idea.mapper.AirlineMapper;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.service.AirlineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AirlineticketIdeaApplicationTests {

	@Autowired
	private AirlineMapper airlineMapper;
	@Test
	void contextLoads() {
	}
   @Test
	public void testSelectWithAirport(){
	   List<AirlineVO> airlines=airlineMapper.selectAirlineListWithAirport();
	   airlines.forEach(System.out::println);
   }

}
