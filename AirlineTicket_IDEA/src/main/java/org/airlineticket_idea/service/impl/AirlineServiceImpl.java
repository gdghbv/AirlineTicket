package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.pojo.Airline;
import org.airlineticket_idea.service.AirlineService;
import org.airlineticket_idea.mapper.AirlineMapper;
import org.springframework.stereotype.Service;

/**
* @author 35461
* @description 针对表【airline】的数据库操作Service实现
* @createDate 2025-05-06 01:29:39
*/
@Service
public class AirlineServiceImpl extends ServiceImpl<AirlineMapper, Airline>
    implements AirlineService{

}




