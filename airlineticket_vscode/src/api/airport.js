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
export const getAirportInfo=(info) =>{
    return request.get('/airport/getUserInfo')
}