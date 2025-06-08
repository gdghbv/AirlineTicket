export const staticRoutes=[
      {
          path: '/',
          name: 'home',
          redirect: '/customerHome',
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
      
         //客户首页
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
          redirect: '/customerCenter/info',
          children: [
            {
              path: 'info',
              name: 'customerCenterInfo',
              component: () => import('@/views/customer_views/CustomerInfo.vue')
            },
            {
              path: 'orders',
              name: 'customerCenterOrders',
              component: () => import('@/views/customer_views/CustomerOrderList.vue')
            }
          ]
        },
        {
          path:'/airport',
          name:'airport',
          component: () => import('@/views/airport_views/AirportView.vue'),
          redirect: '/airport/airportHome',
          children: [
            {
              path:'airportHome',
              name:'airportHome',
              component: () => import('@/views/airport_views/AirportHomeView.vue'),
            },
            {
              path:'airportAirline',
              name:'airportAirline',
              component: () => import('@/views/airport_views/AirportAirlineView.vue'),
            },
            {
              path:'airportAirplane',
              name:'airportAirplane',
              component: () => import('@/views/airport_views/AirportAirplaneView.vue'),
            },
            {
              path:'airportOrder',
              name:'airportOrder',
              component: () => import('@/views/airport_views/AirportOrderView.vue'),
            },
            {
              path:'airportCenter',
              name:'airportCenter',
              component: () => import('@/views/airport_views/AirportCenterView.vue'),
            },
            {
              path:'airportCustomerManage',
              name:'airportCustomerManage',
              component: () => import('@/views/airport_views/AirportCustomerManageVIew.vue'),
            }
          ]
        }
]