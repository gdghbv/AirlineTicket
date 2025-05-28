package org.airlineticket_idea.service;

import org.airlineticket_idea.pojo.Airline;
import com.baomidou.mybatisplus.extension.service.IService;
import org.airlineticket_idea.pojo.dto.AirlineKeywords;
import org.airlineticket_idea.utils.Result;

/**
* @author 35461
* @description 针对表【airline】的数据库操作Service
* @createDate 2025-05-06 01:29:39
*/
public interface AirlineService extends IService<Airline> {

    Result getAirlines(AirlineKeywords airlineKeywords,boolean isAirport);

    Result addAirline(Airline airline);

    Result updateAirline(Airline airline);

    Result deleteAirline(Integer id);
}
