package org.justworking.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName airline
 */
@TableName(value ="airline")
@Data
public class Airline {
    private String airlineid;

    private String airplaneid;

    private String arrivalairportid;

    private String departureairportid;

    private String boardinggate;

    private BigDecimal price;

    private Date departuretime;

    private Date arrivaltime;

    private Date date;

    private Integer firstseat;

    private Integer secondseat;

    private Integer thirdseat;

    private String duration;
}