import request from '@/utils/request'

export const getAirportLogin =(info)=>{
    return request.post('/airport/login', info);
};
export const getCustomerLogin =(info)=>{
    return request.post('/customer/login', info);
};
export const CustomerRegister=(customerInfo)=>{
    return request.post('/customer/register', customerInfo);
}
export const AirportRegister=(airportInfo)=>{
    return request.post('/airport/register', airportInfo);
}