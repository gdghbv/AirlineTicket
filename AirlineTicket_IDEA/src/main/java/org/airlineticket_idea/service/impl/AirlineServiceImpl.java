package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.mapper.AirplaneMapper;
import org.airlineticket_idea.pojo.Airline;
import org.airlineticket_idea.pojo.Airplane;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.pojo.dto.AirlineKeywords;
import org.airlineticket_idea.service.AirlineService;
import org.airlineticket_idea.mapper.AirlineMapper;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 35461
* @description 针对表【airline】的数据库操作Service实现
* @createDate 2025-05-06 01:29:39
*/
@Service
public class AirlineServiceImpl extends ServiceImpl<AirlineMapper, Airline>
    implements AirlineService{
@Autowired
private AirlineMapper airlineMapper;
@Autowired
private AirplaneMapper airplaneMapper;
    @Override
    public Result getAirlines(AirlineKeywords airlineKeywords,boolean isAirport) {
        IPage<AirlineVO> page = new Page<>(airlineKeywords.getPageNum(), airlineKeywords.getPageSize());
        QueryWrapper<AirlineVO> queryWrapper = new QueryWrapper<>();
        if (airlineKeywords.getDepartureKeyword() != null && airlineKeywords.getDepartureKeyword().length() > 0) {
            queryWrapper.like("a.departure", airlineKeywords.getDepartureKeyword());
        }
        if (airlineKeywords.getArrivalKeyword() != null && airlineKeywords.getArrivalKeyword().length() > 0) {
            queryWrapper.like("a.arrival", airlineKeywords.getArrivalKeyword());
        }
        if (airlineKeywords.getDateKeyword() != null) {
            queryWrapper.eq("a.date", airlineKeywords.getDateKeyword());
        }
        // 排除起飞时间距离当前时间不足3小时的航班
        if(!isAirport){
        queryWrapper.and(wrapper -> wrapper.eq("a.date", LocalDate.now())
                .gt("a.departure_time", LocalTime.now().plusHours(3))
                .or()
                .gt("a.date", LocalDate.now())
        );}
        // 排序
        queryWrapper.orderByAsc("a.date", "a.departure_time");
        airlineMapper.selectAirlineListWithPage(page, queryWrapper);
        List<AirlineVO> record = page.getRecords();
        Map data = new HashMap();
        data.put("pageData", record);
        data.put("pageNum", page.getCurrent());
        data.put("pageSize", page.getSize());
        data.put("totalPage", page.getPages());
        data.put("totalSize", page.getTotal());
        return Result.ok(data);
    }

    @Override
    public Result addAirline(Airline airline) {
       Airplane airplane = airplaneMapper.selectById(airline.getAirplaneId());
       airline.setFirstSeat(airplane.getFirstSeat());
       airline.setSecondSeat(airplane.getSecondSeat());
       airline.setThirdSeat(airplane.getThirdSeat());
       airlineMapper.insert(airline);
        return Result.ok(null);
    }

    @Override
    public Result updateAirline(Airline airline) {
       airlineMapper.updateById(airline);
        return Result.ok(null);
    }

    @Override
    public Result deleteAirline(Integer id) {
        airlineMapper.deleteById(id);
        return Result.ok(null);
    }
}




