package org.airlineticket_idea.pojo.vo;

import lombok.Data;

@Data
public class AirportUserVO {
    private Integer userId;

    private String phone;

    private String password;

    private String userName;

    private Integer airportId;

    private String airportPhone;

    private String address;

    private String airportName;
}

