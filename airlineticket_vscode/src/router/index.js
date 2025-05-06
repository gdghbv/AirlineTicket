import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import UserCenterView from '@/views/UserCenterView.vue'
import AdminHomeView from '@/views/AdminHomeView.vue'
import AdminLayout from '@/views/AdminLayout.vue'
import AdminTicketView from '@/views/AdminTicketView.vue'
import AdminOrderView from '@/views/AdminOrderView.vue'
import AdminPlaneView from '@/views/AdminPlaneView.vue'
import AdminAdminUserView from '@/views/AdminAdminUserView.vue'
import AdminUserView from '@/views/AdminUserView.vue'
import OrderView from '@/views/OrderView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/user',
      name: 'user',
      component: UserCenterView
    },
    {
      path: '/order',
      name: 'order',
      component: OrderView
    },
    {
      path: '/admin',
      component: AdminLayout,
      children: [
        { path: '', name: 'admin', component: AdminHomeView },
        { path: 'ticket', name: 'admin-ticket', component: AdminTicketView },
        { path: 'order', name: 'admin-order', component: AdminOrderView },
        { path: 'plane', name: 'admin-plane', component: AdminPlaneView },
        { path: 'admin-user', name: 'admin-admin-user', component: AdminAdminUserView },
        { path: 'user', name: 'admin-user', component: AdminUserView }
      ]
    }
  ]
})

export default router