package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.pojo.Order;
import org.airlineticket_idea.service.OrderService;
import org.airlineticket_idea.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author 35461
* @description 针对表【order】的数据库操作Service实现
* @createDate 2025-05-05 23:50:51
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




