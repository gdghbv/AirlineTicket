package org.airlineticket_idea.service;

import org.airlineticket_idea.pojo.AirportUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.airlineticket_idea.utils.Result;

/**
* @author 35461
* @description 针对表【airport_user】的数据库操作Service
* @createDate 2025-05-24 14:32:47
*/
public interface AirportUserService extends IService<AirportUser> {
    Result register(AirportUser airportUser);

    Result login(AirportUser airportUser);


    Result admin(String token);

    Result updateAdmin(AirportUser airportUser);

    Result deleteAdmin(Integer id);


    Result getUserInfo(String token);
}
