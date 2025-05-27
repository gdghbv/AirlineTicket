package org.airlineticket_idea.pojo.dto;

import lombok.Data;

import java.sql.Date;


@Data
public class AirlineKeywords {
    private String departureKeyword;
    private String arrivalKeyword;
    private Date dateKeyword;
    private Integer pageNum=1;
    private Integer pageSize=10;
}
