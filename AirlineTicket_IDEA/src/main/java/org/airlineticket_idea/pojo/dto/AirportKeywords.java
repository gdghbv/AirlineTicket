package org.airlineticket_idea.pojo.dto;

import lombok.Data;

@Data
public class AirportKeywords {
    private String emailKeyword;
    private String phone;
    private String airportName;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
