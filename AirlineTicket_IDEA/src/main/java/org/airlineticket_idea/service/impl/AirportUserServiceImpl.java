package org.airlineticket_idea.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.mapper.AirportUserMapper;
import org.airlineticket_idea.pojo.AirportUser;
import org.airlineticket_idea.pojo.vo.AirportUserVO;
import org.airlineticket_idea.service.AirportUserService;
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
 * @description 针对表【airport_user】的数据库操作Service实现
 * @createDate 2025-05-24 14:32:47
 */
@Service
public class AirportUserServiceImpl extends ServiceImpl<AirportUserMapper, AirportUser>
        implements AirportUserService {

    @Autowired
    private AirportUserMapper airportUserMapper;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result register(AirportUser airportUser) {
        LambdaQueryWrapper<AirportUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AirportUser::getPhone, airportUser.getPhone());
        if (airportUserMapper.selectCount(queryWrapper) > 0) {
            return Result.build(null, ResultCodeEnum.PHONE_USED);
        }
        airportUser.setPassword(MD5Util.encrypt(airportUser.getPassword()));

        airportUserMapper.insert(airportUser);
        return Result.ok(null);
    }

    @Override
    public Result login(AirportUser airportUser) {

        LambdaQueryWrapper<AirportUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AirportUser::getPhone, airportUser.getPhone());
        AirportUser loginAirportUser = airportUserMapper.selectOne(queryWrapper);
        if (loginAirportUser == null) {
            return Result.build(null, ResultCodeEnum.PHONE_ERROR);
        }
        if (!StringUtils.isEmpty(airportUser.getPassword()) && MD5Util.encrypt(airportUser.getPassword()).equals(loginAirportUser.getPassword())) {
            String token = jwtHelper.createToken(Long.valueOf(loginAirportUser.getUserId()));
            Map data = new HashMap();
            data.put("token", token);
            return Result.ok(data);
        } else {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
    }

    @Override
    public Result admin(String token) {
        System.out.println("-------!!!------"+jwtHelper.getUserId(token));
        AirportUser airportUser = airportUserMapper.selectById(jwtHelper.getUserId(token));
       QueryWrapper<AirportUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("au.airport_id", airportUser.getAirportId());
        IPage<AirportUserVO> page=new Page<>(1,Long.MAX_VALUE);

        airportUserMapper.selectAirportUserByQuery(page,queryWrapper);

        return Result.ok(page.getRecords());
    }

    @Override
    public Result updateAdmin(AirportUser airportUser) {
        airportUserMapper.updateById(airportUser);
        return Result.ok(null);
    }

    @Override
    public Result deleteAdmin(Integer id) {
        airportUserMapper.deleteById(id);
        return Result.ok(null);
    }


    @Override
    public Result getUserInfo(String token) {

        int userId = jwtHelper.getUserId(token).intValue();
        System.out.println("--------------" + userId);
        IPage<AirportUserVO> page=new Page<>(1,10);
        QueryWrapper<AirportUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        airportUserMapper.selectAirportUserByQuery(page,queryWrapper);

        return Result.ok(page.getRecords());
    }

}




