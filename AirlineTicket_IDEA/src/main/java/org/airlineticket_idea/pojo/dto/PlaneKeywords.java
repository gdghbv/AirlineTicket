package org.airlineticket_idea.pojo.dto;

import lombok.Data;

@Data
public class PlaneKeywords {
  private Integer idKeyword;
  private String nameKeyword;
  private String statusKeyword;
  private String locationKeyword;
  private Integer pageNum=1;
  private Integer pageSize =10;
}
