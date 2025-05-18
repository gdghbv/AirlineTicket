package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.pojo.Airline;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.pojo.dto.PageKeywords;
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
    @Override
    public Result getAirlines(PageKeywords pageKeywords) {
        IPage<Map> page = new Page<>(pageKeywords.getPageNum(), pageKeywords.getPageSize());
        QueryWrapper<AirlineVO> queryWrapper = new QueryWrapper<>();
        if (pageKeywords.getDepartureKeyword() != null && pageKeywords.getDepartureKeyword().length() > 0) {
            queryWrapper.like("a.departure", pageKeywords.getDepartureKeyword());
        }
        if (pageKeywords.getArrivalKeyword() != null && pageKeywords.getArrivalKeyword().length() > 0) {
            queryWrapper.like("a.arrival", pageKeywords.getArrivalKeyword());
        }
        if (pageKeywords.getDateKeyword() != null) {
            queryWrapper.eq("a.date", pageKeywords.getDateKeyword());
        }
        // 排除起飞时间距离当前时间不足3小时的航班
        queryWrapper.and(wrapper -> wrapper.eq("a.date", LocalDate.now())
                .gt("a.departure_time", LocalTime.now().plusHours(3))
                .or()
                .gt("a.date", LocalDate.now())
        );
        // 排序
        queryWrapper.orderByAsc("a.date", "a.departure_time");
        airlineMapper.selectAirlineListWithPage(page, pageKeywords, queryWrapper);
        List<Map> record = page.getRecords();
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




