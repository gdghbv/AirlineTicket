package org.airlineticket_idea.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.airlineticket_idea.pojo.Customer;
import org.airlineticket_idea.pojo.dto.UserKeywords;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author 35461
 * @description 针对表【customer】的数据库操作Mapper
 * @createDate 2025-05-05 01:45:29
 * @Entity org.airlineticket_idea.pojo.Customer
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    IPage<Map> selectListWithPageAndSelector(IPage page,  @Param("ew") LambdaQueryWrapper<Customer> wrapper);
}




