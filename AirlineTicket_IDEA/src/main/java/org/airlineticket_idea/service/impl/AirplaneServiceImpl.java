package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.pojo.Airplane;
import org.airlineticket_idea.pojo.Airport;
import org.airlineticket_idea.pojo.dto.PlaneKeywords;
import org.airlineticket_idea.service.AirplaneService;
import org.airlineticket_idea.mapper.AirplaneMapper;
import org.airlineticket_idea.utils.JwtHelper;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 35461
* @description 针对表【airplane】的数据库操作Service实现
* @createDate 2025-05-05 01:45:29
*/
@Service
public class AirplaneServiceImpl extends ServiceImpl<AirplaneMapper, Airplane>
    implements AirplaneService{

    @Autowired
    private AirplaneMapper airplaneMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public Result planes(PlaneKeywords planeKeywords, String token) {

        IPage<Map> page =new Page<>(planeKeywords.getPageNum(), planeKeywords.getPageSize());
        LambdaQueryWrapper<Airplane> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Airplane::getBelongedAirportId,jwtHelper.getUserId(token));
        if(planeKeywords.getNameKeyword()!=null&& planeKeywords.getNameKeyword().length()>0){
            queryWrapper.like(Airplane::getAirplaneName,planeKeywords.getNameKeyword());
        }
        if(planeKeywords.getStatusKeyword()!=null&& planeKeywords.getStatusKeyword().length()>0){
            queryWrapper.like(Airplane::getStatus,planeKeywords.getStatusKeyword());
        }
        if(planeKeywords.getLocationKeyword()!=null&& planeKeywords.getLocationKeyword().length()>0){
            queryWrapper.like(Airplane::getLocation,planeKeywords.getLocationKeyword());
        }
        if(planeKeywords.getIdKeyword()!=null&& planeKeywords.getIdKeyword()>0){
              queryWrapper.eq(Airplane::getAirplaneId,planeKeywords.getIdKeyword());
        }
        queryWrapper.orderByDesc(Airplane::getAirplaneId);
        airplaneMapper.selectAirplanesListWithPage(page,queryWrapper);

        Map data = new HashMap();
        data.put("pageData", page.getRecords());
        data.put("pageNum", page.getPages());
        data.put("totalPage", page.getPages());
        data.put("totalSize", page.getTotal());
        return Result.ok(data);

    }

    @Override
    public Result addPlane(Airplane airplane) {
        airplaneMapper.insert(airplane);
        return Result.ok(null);
    }

    @Override
    public Result updatePlane(Airplane airplane) {
        airplaneMapper.updateById(airplane);
        return Result.ok(null);
    }

    @Override
    public Result deletePlane(Integer id) {
        airplaneMapper.deleteById(id);
        return Result.ok(null);
    }
}





