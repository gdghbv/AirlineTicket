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
    private String orderid;

    private String flightid;

    private String orderstat;

    private BigDecimal spending;

    private Date bookingtime;

    private String airlineid;

    private String istraveled;

    private String seatid;

    private String seattype;
}