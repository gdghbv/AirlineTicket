
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
          path:'/customerHome',
          name:'customerHome',
          component: () => import('@/views/customer_views/CustomerHomeView.vue'),
        },{
          path:'/airportHome',
          name:'airportHome',
          component: () => import('@/views/airport_views/AirportHomeView.vue'),
        }
        
]