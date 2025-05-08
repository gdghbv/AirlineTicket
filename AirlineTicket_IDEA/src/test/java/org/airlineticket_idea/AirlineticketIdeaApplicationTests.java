package org.airlineticket_idea;

import org.airlineticket_idea.mapper.AirlineMapper;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.service.AirlineService;
import org.airlineticket_idea.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AirlineticketIdeaApplicationTests {

	@Autowired
	private AirlineMapper airlineMapper;
	@Autowired
	private OrderService orderService;
	@Test
	void contextLoads() {
	}
   @Test
	public void testSelectWithAirport(){
	   List<AirlineVO> airlines=airlineMapper.selectAirlineListWithAirport();
	   airlines.forEach(System.out::println);
   }
   @Test
	public void testGetSeatId(){
	   System.out.println(orderService.getSeatId(1,"A"));
	   System.out.println(orderService.getSeatId(2,"A"));
	   System.out.println(orderService.getSeatId(3,"A"));
	   System.out.println(orderService.getSeatId(4,"A"));
	   System.out.println(orderService.getSeatId(5,"A"));
	   System.out.println(orderService.getSeatId(6,"A"));
	   System.out.println(orderService.getSeatId(7,"A"));
	   System.out.println(orderService.getSeatId(8,"A"));
	   System.out.println(orderService.getSeatId(9,"A"));
	   System.out.println(orderService.getSeatId(10,"A"));
	   System.out.println(orderService.getSeatId(11,"A"));
	   System.out.println(orderService.getSeatId(12,"A"));
	   System.out.println(orderService.getSeatId(13,"A"));
	   System.out.println(orderService.getSeatId(14,"A"));
   }

}
