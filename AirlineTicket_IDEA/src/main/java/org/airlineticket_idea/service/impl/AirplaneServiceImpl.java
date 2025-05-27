package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.mapper.AirportMapper;
import org.airlineticket_idea.pojo.Airplane;
import org.airlineticket_idea.pojo.Airport;
import org.airlineticket_idea.pojo.dto.PlaneKeywords;
import org.airlineticket_idea.pojo.vo.ShowAirplaneVO;
import org.airlineticket_idea.service.AirplaneService;
import org.airlineticket_idea.mapper.AirplaneMapper;
import org.airlineticket_idea.utils.JwtHelper;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private AirportMapper   airportMapper;
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
        List<Map> records = page.getRecords();
        Map data = new HashMap();
        data.put("pageData", records);
        data.put("pageNum", page.getPages());
        data.put("totalPage", page.getPages());
        data.put("totalSize", page.getTotal());
        return Result.ok(data);

    }

    @Override
    public Result addPlane(Airplane airplane,String token) {
        Airport airport = airportMapper.selectById(jwtHelper.getUserId(token));
        airplane.setBelongedAirportId(airport.getAirportId());
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

    @Override
    public Result showPlaneStat(String token) {
        Integer airportId = jwtHelper.getUserId(token).intValue();
        Map<String, Long>countPlanes=airplaneMapper.selectList(new LambdaQueryWrapper<Airplane>().eq(Airplane::getBelongedAirportId, airportId))
                .stream().collect(Collectors.groupingBy(Airplane::getStatus, Collectors.counting()));
         ShowAirplaneVO showAirplaneVO = new ShowAirplaneVO();
         showAirplaneVO.setMaintaining(countPlanes.getOrDefault("维护中", 0L).intValue());
         showAirplaneVO.setOnLand(countPlanes.getOrDefault("已着陆", 0L).intValue());
         showAirplaneVO.setInFlight(countPlanes.getOrDefault("航行中", 0L).intValue());
 /*       System.out.println(countPlanes);
         System.out.println(showAirplaneVO);*/
        return Result.ok(showAirplaneVO);
    }
}





