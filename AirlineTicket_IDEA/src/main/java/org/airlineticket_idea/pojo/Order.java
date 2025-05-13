package org.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @TableName order
 */
@TableName(value = "`order`")
@Data
public class Order {
    @TableId(value = "order_id")
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
}