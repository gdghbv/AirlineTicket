import request from '@/utils/request.js'

// 分页获取航班列表（POST /getAirline）
export function getFlightsPageInfo(params) {
  // 参数转换为接口需要的格式
  const body = {
    departureKeyword: params.departure || '',
    arrivalKeyword: params.arrival || '',
    dateKeyword: params.date || '',
    pageNum: params.pageNum || 1,
    pageSize: params.pageSize || 10
  }
  return request({
    url: '/getAirline',
    method: 'post',
    data: body
  })
}
//机场获得当前用户的信息
export const getAirportInfo=(info) =>{
    return request.get('/airport/getUserInfo')
}
//机场删除航班

//机场添加航班

//机场修改航班

//机场查询所有管理员信息

//机场修改管理员信息

//机场删除管理员

//机场查询所有客户

//机场修改客户信息

//机场删除客户

//机场查询飞机

//机场添加飞机

//机场修改飞机

//机场删除飞机