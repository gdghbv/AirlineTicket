package org.airlineticket_idea.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AirlineVO {
    // 航班表原有字段
    private String airlineId;
    private String airplaneId;
    private String arrivalAirportId;
    private String departureAirportId;
    private String boardingGate;
    private BigDecimal price;
    private Date departureTime;
    private Date arrivalTime;
    private Date date;
    private Integer firstSeat;
    private Integer secondSeat;
    private Integer thirdSeat;
    private String duration;
    private String departure;
    private String arrival;
    // 新增关联字段（机场名称）
    private String departureAirportName; // 出发机场名称
    private String departureAirportAddress; // 出发机场地址
    private String departureAirportPhone; // 出发机场电话
    private String arrivalAirportName;   // 到达机场名称
    private String arrivalAirportAddress; // 到达机场地址
    private String arrivalAirportPhone;   // 到达机场电话
}
