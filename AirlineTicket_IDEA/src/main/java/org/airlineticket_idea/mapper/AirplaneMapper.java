package org.airlineticket_idea.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.airlineticket_idea.pojo.Airplane;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.airlineticket_idea.pojo.dto.PlaneKeywords;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author 35461
* @description 针对表【airplane】的数据库操作Mapper
* @createDate 2025-05-05 01:45:29
* @Entity org.airlineticket_idea.pojo.Airplane
*/
public interface AirplaneMapper extends BaseMapper<Airplane> {

    IPage<Map> selectAirplanesListWithPage(IPage page,  @Param("ew") LambdaQueryWrapper<Airplane> queryWrapper);
}




