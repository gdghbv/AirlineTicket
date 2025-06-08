package org.airlineticket_idea;

import org.airlineticket_idea.mapper.AirlineMapper;
import org.airlineticket_idea.mapper.AirportUserMapper;
import org.airlineticket_idea.pojo.AirportUser;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.service.AirlineService;
import org.airlineticket_idea.service.AirplaneService;
import org.airlineticket_idea.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class AirlineticketIdeaApplicationTests {

	@Autowired
	private AirlineMapper airlineMapper;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AirplaneService	airplaneService;
	@Autowired
	private AirlineService airlineService;
    @Autowired
    private AirportUserMapper airportUserMapper;

	@Test
	void contextLoads() {
	}
	@Test
	public void getUser(){
		AirportUser user=airportUserMapper.selectById(2);
		System.out.println(user);
	}
/*   @Test
	public void testSelectWithAirport(){
	   List<AirlineVO> airlines=airlineMapper.selectAirlineListWithAirport();
	   airlines.forEach(System.out::println);
   }*/
//   @Test
//	public void testGetSeatId(){
//	   System.out.println(orderService.getSeatId(1,"A"));
//	   System.out.println(orderService.getSeatId(2,"A"));
//	   System.out.println(orderService.getSeatId(3,"A"));
//	   System.out.println(orderService.getSeatId(4,"A"));
//	   System.out.println(orderService.getSeatId(5,"A"));
//	   System.out.println(orderService.getSeatId(6,"A"));
//	   System.out.println(orderService.getSeatId(7,"A"));
//	   System.out.println(orderService.getSeatId(8,"A"));
//	   System.out.println(orderService.getSeatId(9,"A"));
//	   System.out.println(orderService.getSeatId(10,"A"));
//	   System.out.println(orderService.getSeatId(11,"A"));
//	   System.out.println(orderService.getSeatId(12,"A"));
//	   System.out.println(orderService.getSeatId(13,"A"));
//	   System.out.println(orderService.getSeatId(14,"A"));
//   }
// @Test
//	public void getAirlineDiscount(){
//		AirlineVO airlineVO=new AirlineVO();
//		airlineVO.setFirstSeatPrice(BigDecimal.valueOf(1500));
//		airlineVO.setSecondSeatPrice(BigDecimal.valueOf(1300));
//		airlineVO.setThirdSeatPrice(BigDecimal.valueOf(1000));
//		orderService.calculatePrice("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSiox099ANDXYNUtJRSq0oULIyNDexMLAwMzU00FEqLU4t8kxRsjKpBQDl71jcLwAAAA.giplYjRZ542wsWPyMzwn1C0rCvzhmLtAiBmZLJPSaLsBUm9baqcorfB-vO_VIsyL1ed65cYGEuKPMlQq8lCVuQ",airlineVO);
//	 System.out.println(airlineVO);
// }
// @Test
//	public void testAirplaneStat(){
//		airplaneService.showPlaneStat("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSiox099ANDXYNUtJRSq0oULIyNDexMDY2NDMx0VEqLU4t8kwBitUCAC9EvVYvAAAA.JQvfqf-bvtGtW6q6oF37aHtqSBMTe302qm-sLOJD7f_DO_DFbKOXqjsnusN_QPPxWTh81AvW53ngig8_L160Pg")
//		;
// }
// @Test
//	public void testAirlineStat(){
//		orderService.showAirlineStat();
// }
}
