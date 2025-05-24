package org.airlineticket_idea.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.pojo.Airport;
import org.airlineticket_idea.pojo.AirportUser;
import org.airlineticket_idea.pojo.Customer;
import org.airlineticket_idea.service.AirportService;
import org.airlineticket_idea.mapper.AirportMapper;
import org.airlineticket_idea.utils.JwtHelper;
import org.airlineticket_idea.utils.MD5Util;
import org.airlineticket_idea.utils.Result;
import org.airlineticket_idea.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 35461
* @description 针对表【airport】的数据库操作Service实现
* @createDate 2025-05-05 01:45:29
*/
@Service
public class AirportServiceImpl extends ServiceImpl<AirportMapper, Airport>
    implements AirportService{

}




