package org.airlineticket_idea.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.pojo.Airport;
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
@Autowired
private AirportMapper airportMapper;

@Autowired
private JwtHelper jwtHelper;
    @Override
    public Result register(Airport airport) {
        LambdaQueryWrapper<Airport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Airport::getAirportName, airport.getAirportName());
        if (airportMapper.selectCount(queryWrapper)>0){
         return Result.build(null, ResultCodeEnum.PHONE_USED );
        }
        airport.setPassword(MD5Util.encrypt(airport.getPassword()));

        airportMapper.insert(airport);
        return Result.ok(null);
    }

    @Override
    public Result login(Airport airport) {

        LambdaQueryWrapper<Airport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Airport::getPhone, airport.getPhone());
        Airport loginAirport = airportMapper.selectOne(queryWrapper);
        if (loginAirport == null) {
            return Result.build(null, ResultCodeEnum.PHONE_ERROR);
        }
        if (!StringUtils.isEmpty(airport.getPassword()) && MD5Util.encrypt(airport.getPassword()).equals(loginAirport.getPassword())) {
            String token = jwtHelper.createToken(Long.valueOf(loginAirport.getAirportId()));
            Map data = new HashMap();
            data.put("token", token);
            return Result.ok(data);
        } else {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
    }

    @Override
    public Result admin(String token) {
        Airport airport = airportMapper.selectById(jwtHelper.getUserId(token));
        LambdaQueryWrapper<Airport> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Airport::getAirportId, airport.getAirportId());

        List<Airport> list =airportMapper.selectList(queryWrapper);
        for(Airport a:list){
            a.setPassword("******");
        }
        return Result.ok(list);
    }

    @Override
    public Result updateAdmin(Airport airport) {
        airportMapper.updateById(airport);
        return Result.ok(null);
    }

    @Override
    public Result deleteAdmin(Integer id) {
        airportMapper.deleteById(id);
        return Result.ok(null);
    }



    @Override
    public Result deleteUser(Integer id) {
        airportMapper.deleteById(id);
        return Result.ok(null);
    }

    @Override
    public Result getUserInfo(String token) {

        int userId = jwtHelper.getUserId(token).intValue();

        Airport airport=airportMapper.selectById(userId);
        airport.setPassword("");

        return Result.ok(airport);
    }


}




