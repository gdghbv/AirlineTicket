import path from 'path';

export const staticRoutes=[
      {
          path: '/',
          name: 'home',
          redirect: '/login',
        },
        {
          path:'/login',
          name:'login',
          component: () => import('@/views/LoginView.vue'),
        },{
          path:'/register',
          name:'register',
          component: () => import('@/views/RegisterView.vue'),
        },
       {
          path:'/airportHome',
          name:'airportHome',
          component: () => import('@/views/airport_views/AirportHomeView.vue'),
        }, //客户首页
        {
          path:'/customerHome',
          name:'customerHome',
          component: () => import('@/views/customer_views/CustomerHomeView.vue'),
        },
        //用户订购机票
        {
          path:'/customerOrder',
          name:'customerOrder',
          component:() => import('@/views/customer_views/CustomerTicketOrder.vue'),
        },
        //客户个人中心
        {
          path:'/customerCenter',
          name:'customerCenter',
          component:() => import('@/views/customer_views/CustomerCenter.vue'),
        },
        // Customer API测试页面
        {
          path: '/testCustomerApi',
          name: 'testCustomerApi',
          component: () => import('@/views/customer_views/TestCustomerApi.vue'),
        }

        
]