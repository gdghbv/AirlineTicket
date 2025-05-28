package org.airlineticket_idea.service;

import org.airlineticket_idea.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.airlineticket_idea.pojo.dto.OrderDTO;
import org.airlineticket_idea.pojo.dto.OrderKeywords;
import org.airlineticket_idea.pojo.dto.AirlineKeywords;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.utils.Result;

/**
* @author 35461
* @description 针对表【order】的数据库操作Service
* @createDate 2025-05-05 23:50:51
*/
public interface OrderService extends IService<Order> {
    Result getHistoryOrder(String token, AirlineKeywords airlineKeywords);

    Result buyTicket(String token, OrderDTO order);

    Result refundTicket(String token, Order order);
    String getSeatId(int seatNum,String seatType);

    Result memberRegister(String token);

    Result calculatePrice(String token, AirlineVO airlineVO);

    Result showAirlineStat();

    Result getOrders(OrderKeywords orderKeywords, String token);

    Result updateOrder(Order order);
}
