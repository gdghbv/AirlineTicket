package org.airlineticket_idea.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.airlineticket_idea.pojo.Airline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.pojo.vo.PageKeywords;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author 35461
* @description 针对表【airline】的数据库操作Mapper
* @createDate 2025-05-06 01:29:39
* @Entity org.airlineticket_idea.pojo.Airline
*/
public interface AirlineMapper extends BaseMapper<Airline> {
List<AirlineVO> selectAirlineListWithAirport();
IPage<Map> selectAirlineListWithPage(IPage page, @Param("pageKeywords")PageKeywords pageKeywords);
}




