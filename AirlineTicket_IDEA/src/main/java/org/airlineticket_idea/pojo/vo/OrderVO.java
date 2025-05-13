package org.airlineticket_idea.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class OrderVO {
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
    //前端传入是否使用优惠，
    private boolean useDiscount;
}
