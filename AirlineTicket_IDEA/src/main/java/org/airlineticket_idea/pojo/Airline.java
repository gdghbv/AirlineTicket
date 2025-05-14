package org.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import lombok.Data;

/**
 * @TableName airline
 */
@TableName(value ="airline")
@Data
public class Airline {
    @TableId(value = "airline_id")
    private Integer airlineId;

    private Integer airplaneId;

    private Integer arrivalAirportId;

    private Integer departureAirportId;

    private String boardingGate;

    private BigDecimal price;

    private Time departureTime;

    private Time arrivalTime;

    private Date date;

    private Integer firstSeat;

    private Integer secondSeat;

    private Integer thirdSeat;

    private String duration;

    private String departure;

    private String arrival;
}