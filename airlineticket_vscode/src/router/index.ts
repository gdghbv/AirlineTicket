import { createRouter, createWebHistory } from 'vue-router'
import { staticRoutes } from './routes'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: staticRoutes,
})


export default router
