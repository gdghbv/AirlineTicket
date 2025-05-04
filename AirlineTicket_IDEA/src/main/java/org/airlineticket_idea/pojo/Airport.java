package org.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName airport
 */
@TableName(value ="airport")
@Data
public class Airport {
    private String airportId;

    private String airportName;

    private String email;

    private String password;

    private String phone;

    private String address;
}