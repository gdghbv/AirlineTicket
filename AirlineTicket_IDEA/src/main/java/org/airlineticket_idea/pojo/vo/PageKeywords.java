package org.airlineticket_idea.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PageKeywords {
    private String departureKeyword;
    private String arrivalKeyword;
    private Date dateKeyword;
    private Integer pageNum=1;
    private Integer pageSize=10;
}
