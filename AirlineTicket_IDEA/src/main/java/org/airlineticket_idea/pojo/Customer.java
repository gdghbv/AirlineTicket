package org.airlineticket_idea.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName customer
 */
@TableName(value ="customer")
@Data
public class Customer {
    @TableId(value = "customer_id")
    private Integer customerId;

    private String userName;

    private String password;

    private String email;

    private String phone;

    private Integer milsPoints;
}