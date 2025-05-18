package org.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName airplane
 */
@TableName(value ="airplane")
@Data
public class Airplane {
    @TableId(value = "airplane_id")
    private Integer airplaneId;

    private String airplaneName;

    private String status;

    private String location;

    private Integer belongedAirportId;

    private Integer firstSeat;

    private Integer secondSeat;

    private Integer thirdSeat;
}