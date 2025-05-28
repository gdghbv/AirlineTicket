package org.airlineticket_idea.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.airlineticket_idea.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author 35461
* @description 针对表【order】的数据库操作Mapper
* @createDate 2025-05-05 23:50:51
* @Entity org.airlineticket_idea.pojo.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    IPage<Map<String, Object>> selectOrderListWithPage(
            IPage<Map<String, Object>> page,
            @Param("ew") QueryWrapper<Order> wrapper
    );
}




