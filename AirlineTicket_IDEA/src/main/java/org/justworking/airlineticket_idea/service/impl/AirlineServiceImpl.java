package org.justworking.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.justworking.airlineticket_idea.pojo.Airline;
import org.justworking.airlineticket_idea.service.AirlineService;
import org.justworking.airlineticket_idea.mapper.AirlineMapper;
import org.springframework.stereotype.Service;

/**
* @author 35461
* @description 针对表【airline】的数据库操作Service实现
* @createDate 2025-05-04 02:40:34
*/
@Service
public class AirlineServiceImpl extends ServiceImpl<AirlineMapper, Airline>
    implements AirlineService{

}




