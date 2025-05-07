package org.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName order
 */
@TableName(value ="order")
@Data
public class Order {
    @TableId(value = "order_id")
    private String orderId;

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