import request from '@/utils/request'
import axios from 'axios'

// 客户向ai询问并返回相应的回答


//不知道为什么request.post请求会报错，
// 但是axios.post请求不会报错
/* export const askAIQuestion=(info)=>{
    return  request.post('/customer/ask', info,{
        headers:{
            'Content-Type':'text/plain'
        },
        transformResponse:[data=>data]
    })
} */

export const askAIQuestion=(info)=>{
return axios.post('/api/customer/ask', info, {
headers:{
            'Content-Type':'text/plain'
        }
})
}

//客户获取航班列表，info是可以传递的搜索值的参数，但是必须传递个json数据，哪怕是空的
export const getAirlineList=(pageKeywords)=>{
    return request.post('/customer/getAirline', pageKeywords)
}

//获取客户的个人信息
export const getCustomerInfo=() =>{
    return request.get('/customer/getUserInfo')
}
//客户修改自己的信息
export const updateCustomerInfo=(customer) =>{
    return request.put('/customer/updateUserInfo', customer)
}

//客户点击购买按钮是计算并返回价格
export const getPrice=(airlineVO)=>{
    return request.post('/customer/getPrice', airlineVO)
}
//客户订购机票
export const orderAirline = (order) => {
    return request.post('/customer/orderAirline', order)
}
//客户进行票退订
export const cancelOrder=(order)=>{
    return request.put('/customer/refundTicket', order)
}
//客户查询自己历史订单信息
export const getCustomerOrderList=(pageKeywords)=>{
    return request.post('/customer/getOrderList', pageKeywords)
}
//客户升级为会员(设置个按钮，按下按钮就升级为会员)
export const memberRegister=()=>{
    return request.put('/customer/memberRegister')
}