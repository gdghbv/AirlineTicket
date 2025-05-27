package org.airlineticket_idea.pojo.dto;

import lombok.Data;

@Data
public class OrderKeywords {
    private Integer orderId;
    private String customerId;
    private String citizenName;
    private String airlineId;
    private Integer pageNum=1;
    private Integer pageSize=10;
}
