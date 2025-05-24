package org.airlineticket_idea.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

@Data
public class AirlineVO {
    @JsonProperty("airlineId")
    private Integer airlineId;

    @JsonProperty("airplaneId")
    private Integer airplaneId;

    @JsonProperty("arrivalAirportId")
    private Integer arrivalAirportId;

    @JsonProperty("departureAirportId")
    private Integer departureAirportId;

    @JsonProperty("boardingGate")
    private String boardingGate;

    private BigDecimal price;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty("departureTime")
    private Time departureTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @JsonProperty("arrivalTime")
    private Time arrivalTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonProperty("firstSeat")
    private Integer firstSeat;

    @JsonProperty("secondSeat")
    private Integer secondSeat;

    @JsonProperty("thirdSeat")
    private Integer thirdSeat;

    private String duration;
    private String departure;
    private String arrival;

    @JsonProperty("departureAirportName")
    private String departureAirportName;

    @JsonProperty("departureAirportAddress")
    private String departureAirportAddress;

    @JsonProperty("departureAirportPhone")
    private String departureAirportPhone;

    @JsonProperty("arrivalAirportName")
    private String arrivalAirportName;

    @JsonProperty("arrivalAirportAddress")
    private String arrivalAirportAddress;

    @JsonProperty("arrivalAirportPhone")
    private String arrivalAirportPhone;

    @JsonProperty("firstSeatPrice")
    private BigDecimal firstSeatPrice;

    @JsonProperty("secondSeatPrice")
    private BigDecimal secondSeatPrice;

    @JsonProperty("thirdSeatPrice")
    private BigDecimal thirdSeatPrice;

    @JsonProperty("firstSeatDiscountPrice")
    private BigDecimal firstSeatDiscountPrice;

    @JsonProperty("secondSeatDiscountPrice")
    private BigDecimal secondSeatDiscountPrice;

    @JsonProperty("thirdSeatDiscountPrice")
    private BigDecimal thirdSeatDiscountPrice;

    @JsonProperty("firstSeatDiscount")
    private BigDecimal firstSeatDiscount;

    @JsonProperty("secondSeatDiscount")
    private BigDecimal secondSeatDiscount;

    @JsonProperty("thirdSeatDiscount")
    private BigDecimal thirdSeatDiscount;
}