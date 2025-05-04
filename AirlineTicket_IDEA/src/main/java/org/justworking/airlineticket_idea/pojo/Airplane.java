package org.justworking.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName airplane
 */
@TableName(value ="airplane")
@Data
public class Airplane {
    private String airplaineid;

    private String airplanename;

    private String status;

    private String location;

    private Integer firstseat;

    private Integer secondseat;

    private Integer thirdseat;
}