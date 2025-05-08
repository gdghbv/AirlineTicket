<template>
  <div class="admin-layout-root">
    <aside class="admin-sidebar">
      <div class="sidebar-title">机场管理</div>
      <div :class="['sidebar-item', activeMain==='home' ? 'active' : '']" @click="goMain('home')">
        <span class="sidebar-icon">🏠</span> 系统首页
      </div>
      <div class="sidebar-group">
        <div class="sidebar-group-title">信息管理</div>
        <div :class="['sidebar-item', activeMain==='ticket' ? 'active' : '']" @click="goMain('ticket')">
          <span class="sidebar-icon">🎫</span> 机票管理
        </div>
        <div :class="['sidebar-item', activeMain==='order' ? 'active' : '']" @click="goMain('order')">
          <span class="sidebar-icon">📦</span> 机票订单
        </div>
        <div :class="['sidebar-item', activeMain==='plane' ? 'active' : '']" @click="goMain('plane')">
          <span class="sidebar-icon">✈️</span> 飞机管理
        </div>
      </div>
      <div class="sidebar-group">
        <div class="sidebar-group-title">用户管理</div>
        <div :class="['sidebar-item', activeMain==='admin-user' ? 'active' : '']" @click="goMain('admin-user')">
          <span class="sidebar-icon">🛡️</span> 管理员信息
        </div>
        <div :class="['sidebar-item', activeMain==='user' ? 'active' : '']" @click="goMain('user')">
          <span class="sidebar-icon">👤</span> 用户信息
        </div>
      </div>
    </aside>
    <main class="admin-main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
const router = useRouter()
const route = useRoute()
const activeMain = computed(() => {
  const map = {
    '/admin': 'home',
    '/admin/ticket': 'ticket',
    '/admin/order': 'order',
    '/admin/plane': 'plane',
    '/admin/admin-user': 'admin-user',
    '/admin/user': 'user'
  }
  return map[route.path] || 'home'
})
function goMain(key) {
  const pathMap = {
    'home': '/admin',
    'ticket': '/admin/ticket',
    'order': '/admin/order',
    'plane': '/admin/plane',
    'admin-user': '/admin/admin-user',
    'user': '/admin/user'
  }
  router.push(pathMap[key])
}
</script>

<style scoped>
.admin-layout-root {
  display: flex;
  min-height: 100vh;
  background: linear-gradient(120deg, #e0e7ff 0%, #f8fafc 100%);
}
.admin-sidebar {
  width: 210px;
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
  color: #fff;
  padding: 32px 0 0 0;
  display: flex;
  flex-direction: column;
  border-radius: 0 28px 28px 0;
  box-shadow: 2px 0 12px #11998e11;
  min-height: 100vh;
}
.sidebar-title {
  font-size: 1.3rem;
  font-weight: 700;
  letter-spacing: 2px;
  margin-bottom: 32px;
  text-align: center;
}
.sidebar-group {
  margin-bottom: 24px;
}
.sidebar-group-title {
  font-size: 1rem;
  font-weight: 600;
  margin: 12px 0 8px 32px;
  opacity: 0.85;
}
.sidebar-item {
  padding: 14px 0 14px 38px;
  font-size: 1.08rem;
  font-weight: 500;
  cursor: pointer;
  border-left: 4px solid transparent;
  transition: background 0.18s, color 0.18s, border 0.18s;
  display: flex;
  align-items: center;
  gap: 10px;
}
.sidebar-item.active, .sidebar-item:hover {
  background: rgba(255,255,255,0.13);
  color: #11998e;
  border-left: 4px solid #fff;
}
.sidebar-icon {
  font-size: 1.2rem;
}
.admin-main-content {
  flex: 1;
  padding: 48px 36px 36px 36px;
  min-height: 100vh;
  background: transparent;
}
@media (max-width: 900px) {
  .admin-layout-root { flex-direction: column; }
  .admin-sidebar { flex-direction: row; width: 100vw; min-width: 0; border-radius: 0 0 28px 28px; }
  .sidebar-item, .sidebar-group-title { padding-left: 18px; }
  .admin-main-content { padding: 24px 6px; }
}
</style>
