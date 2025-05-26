package org.airlineticket_idea.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.airlineticket_idea.pojo.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Integer orderId;

    private String orderStat;

    private BigDecimal spending;

    private LocalDateTime bookingTime;

    private Integer airlineId;

    private String seatId;

    private String seatType;

    private Integer customerId;
    private String citizenId;
    private String citizenName;

    private boolean useDiscount;

    private BigDecimal discount;
    public Order toOrder() {
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setOrderStat(this.orderStat);
        order.setSpending(this.spending);
        order.setBookingTime(this.bookingTime);
        order.setAirlineId(this.airlineId);
        order.setSeatId(this.seatId);
        order.setSeatType(this.seatType);
        order.setCustomerId(this.customerId);
        order.setCitizenId(this.citizenId);
        order.setCitizenName(this.citizenName);
        return order;
    }
}