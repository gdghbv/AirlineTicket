import request from '@/utils/request.js'



//机场获得当前用户的信息
export const getAirportInfo=() =>{
    console.log("当前的token是", localStorage.getItem('vue_admin_template_token'));
    return request.get('/airport/getUserInfo')
}

//机场获取当前管理员信息
export const getCurrentAirportInfo=() =>{
    return request.get('/airport/getCurrentAirportInfo')
}

//机场获得航班
export const getAirlineList=(pageKeywords)=>{
    return request.post('/airport/airlines', pageKeywords)
}
//机场删除航班
export const deleteAirline=(id)=>{
    return request.delete('/airport/deleteAirline', {params:{id}})
}
//机场添加航班
export const addAirline=(airline)=>{
    return request.post('/airport/addAirline', airline)
}
//机场修改航班
export const updateAirline=(airline)=>{
    return request.put('/airport/updateAirline', airline)
}
//机场查询所有管理员信息
export const getAllAirportUsers=()=>{
    return request.get('/airport/airportUsers')
}
//机场修改管理员信息
export const updateAdmin=(airportUser)=>{
    return request.put('/airport/updateAirportUser', airportUser)
}
//机场删除管理员
export const deleteAirportUser=(id)=>{
    return request.delete('/airport/deleteAirportUser', {params:{id}})
}
//机场查询所有客户
export const getAllCustomers=(customerKeywords)=>{
    return request.post('/airport/users', customerKeywords)
}
//机场修改客户信息
export const updateCustomer=(customer)=>{
    return request.put('/airport/updateUser', customer)
}
//机场删除客户
export const deleteCustomer=(id)=>{
    return request.post('/airport/deleteUser', null, {params:{id}})
}
//机场查询飞机
export const getAllAirplanes=(planeKeywords)=>{
    return request.post('/airport/planes',planeKeywords)
}
//机场添加飞机
export const addAirplane=(airplane)=>{
    return request.post('/airport/addPlane', airplane)
}
//机场修改飞机
export const updateAirplane=(airplane)=>{
    return request.put('/airport/updatePlane', airplane)
}
//机场删除飞机
export const deleteAirplane=(id)=>{
    return request.delete('/airport/deletePlane', {params:{id}})
}

//机场获取订单最多前五名的航班信息
export const getTopFiveAirlines=()=>{
    return request.get('/airport/topFiveAirlines')
}
//机场获取所有飞机的情况
export const getAllAirplanesStat=()=>{
    return request.get('/airport/airplanesStat')
}
//获取机场的用户数量和订单数量
export const getAirportUserAndOrderCount=()=>{
    return request.get('/airport/userAndOrderCount')
}
export const getAirportOrders=(orderKeywords)=>{
    return request.post('/airport/orders', orderKeywords)
}
export const updateOrder=(order)=>{
    return request.put('/airport/updateOrder', order)
}