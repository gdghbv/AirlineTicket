package org.airlineticket_idea.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class OrderVO {
    private String orderId;

    private String orderStat;

    private BigDecimal spending;

    private LocalDateTime bookingTime;

    private String airlineId;

    private String seatId;

    private String seatType;

    private String customerId;
    private String citizenId;
    private String citizenName;
    //前端传入是否使用优惠，
    private boolean useDiscount;
}
