import request from '@/utils/request'

export const getCustomerInfo=(info) =>{
    return request.get('/customer/getUserInfo')
}