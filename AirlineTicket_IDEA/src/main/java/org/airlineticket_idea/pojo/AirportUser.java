package org.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName airport_user
 */
@TableName(value ="airport_user")
@Data
public class AirportUser {
    @TableId(value ="user_id")
    private Integer userId;

    private String phone;

    private String password;

    private String userName;

    private Integer airportId;
}