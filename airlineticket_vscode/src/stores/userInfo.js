
import { defineStore } from 'pinia';
import { getToken, removeToken, setToken } from '../utils/token-utils';
import { getAirportLogin,getCustomerLogin } from '../api/index';
import { getCustomerInfo } from '../api/customer';
import { getAirportInfo } from '../api/airport';



/**
 * 用户信息
 * @methods setUserInfos 设置用户信息
 */
export const useUserInfoStore = defineStore('userInfo', {

	state: () => ({
    token: getToken(),
    name:'',
    phone: '',
    email:'',
    id: '',
    milsPoints:''
  }),

	actions: {
    // 登陆的异步action
    async customerLogin (loginForm) {
       // 发送登陆的请求
      const result = await getCustomerLogin(loginForm)
      // 请求成功后, 取出token保存  pinia和local中
      const token = result.token
      this.token = token
      setToken(token)
    },
    //机场登录
    async airportLogin(loginForm){
    const result =await getAirportLogin(loginForm)
    const token=result.token
    this.token=token
    setToken(token)
    },
    //获取当前客户用户信息
    async getCustomerInfo () {
      const result = await getCustomerInfo()
      this.name = result.userName
      this.phone = result.phone
      this.email = result.email
      this.id=result.customerId
      this.milsPoints=result.milsPoints
    },
    //获取当前机场用户信息
    async getAirportInfo(){
      const result = await getAirportInfo()
      this.name = result.airportName
      this.phone = result.phone
      this.email = result.email
      this.id=result.airportUserId
    },
    //当退出登录时，清除用户信息
    initUserInfo(){
      removeToken()
      this.name = ''
      this.phone = ''
      this.email = ''
      this.name = ''
      this.id = ''
      this.milsPoints = ''
      console.log('1111111111');
      
    }

  },
  

});