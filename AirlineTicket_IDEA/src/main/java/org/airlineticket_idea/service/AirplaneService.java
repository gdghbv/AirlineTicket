package org.airlineticket_idea.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.airlineticket_idea.pojo.Airplane;
import com.baomidou.mybatisplus.extension.service.IService;
import org.airlineticket_idea.pojo.Airport;
import org.airlineticket_idea.pojo.dto.PlaneKeywords;
import org.airlineticket_idea.utils.Result;

import java.util.Map;

/**
* @author 35461
* @description 针对表【airplane】的数据库操作Service
* @createDate 2025-05-05 01:45:29
*/
public interface AirplaneService extends IService<Airplane> {

    Result planes(PlaneKeywords planeKeywords, String token);


    Result addPlane(Airplane airplane,String token);

    Result updatePlane(Airplane airplane);

    Result deletePlane(Integer id);

    Result showPlaneStat(String token);
}
