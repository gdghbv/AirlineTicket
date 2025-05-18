package org.airlineticket_idea.pojo.dto;

import lombok.Data;

@Data
public class UserKeywords {
  private Integer idKeywords;
  private String phoneKeywords;
  private String nameKeywords;
  private String emailKeywords;
  private Integer pageNum=1;
  private Integer pageSize=10;
}
