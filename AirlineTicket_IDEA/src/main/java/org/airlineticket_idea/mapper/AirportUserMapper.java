package org.airlineticket_idea.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.airlineticket_idea.pojo.AirportUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.airlineticket_idea.pojo.vo.AirportUserVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 35461
* @description 针对表【airport_user】的数据库操作Mapper
* @createDate 2025-05-24 14:32:47
* @Entity org.airlineticket_idea.pojo.AirportUser
*/
public interface AirportUserMapper extends BaseMapper<AirportUser> {
IPage<AirportUserVO> selectAirportUserByQuery(IPage page, @Param("ew") QueryWrapper<AirportUser> queryWrapper);
}




