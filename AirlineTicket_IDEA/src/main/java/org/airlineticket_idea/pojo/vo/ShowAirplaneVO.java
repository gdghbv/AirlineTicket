package org.airlineticket_idea.pojo.vo;

import lombok.Data;

//用来展示飞机信息饼图的VO
@Data
public class ShowAirplaneVO {
    //表示维修中的飞机数量
   private Integer maintaining;
//表示航行中飞机的数量
    private Integer inFlight;

    //已经在陆地上的飞机数量
    private Integer onLand;
}
