package org.airlineticket_idea.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderVO {
    private String orderId;

    private String flightId;

    private String orderStat;

    private BigDecimal spending;

    private Date bookingTime;

    private String airlineId;

    private String isTraveled;

    private String seatId;

    private String seatType;

    private String customerId;
    private String citizenId;
    private String citizenName;

}
