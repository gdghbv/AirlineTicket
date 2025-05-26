package org.airlineticket_idea.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

@Data
public class AirlineVO {

    private Integer airlineId;


    private Integer airplaneId;


    private Integer arrivalAirportId;


    private Integer departureAirportId;


    private String boardingGate;

    private BigDecimal price;


    private Time departureTime;


    private Time arrivalTime;


    private LocalDate date;


    private Integer firstSeat;


    private Integer secondSeat;


    private Integer thirdSeat;

    private String duration;
    private String departure;
    private String arrival;


    private String departureAirportName;


    private String departureAirportAddress;

    private String departureAirportPhone;


    private String arrivalAirportName;


    private String arrivalAirportAddress;


    private String arrivalAirportPhone;


    private BigDecimal firstSeatPrice;


    private BigDecimal secondSeatPrice;


    private BigDecimal thirdSeatPrice;


    private BigDecimal firstSeatDiscountPrice;


    private BigDecimal secondSeatDiscountPrice;


    private BigDecimal thirdSeatDiscountPrice;


    private BigDecimal firstSeatDiscount;


    private BigDecimal secondSeatDiscount;

    private BigDecimal thirdSeatDiscount;
}