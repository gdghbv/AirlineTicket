<template>
  <nav class="navbar">
    <div class="navbar-left">
      <div class="navbar-logo" @click="$router.push('/')">
        <img src="/favicon.ico" alt="logo" />
        <span class="navbar-title">订票助手 AI</span>
      </div>
    </div>
    <div class="navbar-center">
      <div :class="['nav-item', isActive('/') ? 'active' : '']" @click="$router.push('/')">首页</div>
      <div :class="['nav-item', isActive('/order') ? 'active' : '']" @click="$router.push('/order')">订购机票</div>
      <div :class="['nav-item', isActive('/user') ? 'active' : '']" @click="$router.push('/user')">个人中心</div>
    </div>
    <div class="navbar-right">
      <div v-if="isLogin" class="avatar-wrapper" @mouseenter="showMenu = true" @mouseleave="showMenu = false">
        <img class="avatar" :src="userAvatar" alt="avatar" />
        <transition name="fade">
          <div v-if="showMenu" class="user-menu">
            <div class="menu-item" @click="logout">退出登录</div>
          </div>
        </transition>
      </div>
      <div v-else class="auth-btns">
        <button class="nav-btn" @click="$router.push('/login')">登录</button>
        <button class="nav-btn" @click="$router.push('/register')">注册</button>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
// 假设有全局登录状态和头像
const isLogin = ref(false) // 实际项目请用pinia/vuex或inject
const userAvatar = computed(() =>
  'https://api.dicebear.com/7.x/identicon/svg?seed=airuser'
)
const showMenu = ref(false)
const router = useRouter()
const route = useRoute()
function isActive(path) {
  return route.path === path
}
function logout() {
  // 这里写退出登录逻辑
  isLogin.value = false
  router.push('/login')
}
</script>

<style scoped>
.navbar {
  width: 100%;
  height: 64px;
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 36px;
  box-shadow: 0 2px 12px 0 #6a82fb22;
  position: sticky;
  top: 0;
  z-index: 100;
  font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
}
.navbar-logo {
  display: flex;
  align-items: center;
  cursor: pointer;
  gap: 10px;
}
.navbar-logo img {
  width: 32px;
  height: 32px;
  border-radius: 8px;
}
.navbar-title {
  font-size: 1.3rem;
  font-weight: 700;
  color: #fff;
  letter-spacing: 2px;
  text-shadow: 0 2px 8px #fc5c7d33;
}
.navbar-center {
  display: flex;
  gap: 38px;
}
.nav-item {
  color: #fff;
  font-size: 1.1rem;
  font-weight: 500;
  padding: 8px 18px;
  border-radius: 18px;
  cursor: pointer;
  transition: background 0.18s, color 0.18s;
  position: relative;
}
.nav-item.active, .nav-item:hover {
  background: rgba(255,255,255,0.18);
  color: #fc5c7d;
}
.navbar-right {
  display: flex;
  align-items: center;
  gap: 18px;
}
.avatar-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  cursor: pointer;
}
.avatar {
  width: 38px;
  height: 38px;
  border-radius: 50%;
  border: 2px solid #fff;
  box-shadow: 0 2px 8px #fc5c7d22;
  background: #fff;
}
.user-menu {
  position: absolute;
  top: 48px;
  right: 0;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 16px #6a82fb22;
  min-width: 120px;
  padding: 8px 0;
  animation: fade-in-menu 0.2s;
}
@keyframes fade-in-menu {
  0% { opacity: 0; transform: translateY(-10px); }
  100% { opacity: 1; transform: translateY(0); }
}
.menu-item {
  padding: 10px 18px;
  color: #6a82fb;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.15s;
}
.menu-item:hover {
  background: #f5f7ff;
  color: #fc5c7d;
}
.auth-btns {
  display: flex;
  gap: 10px;
}
.nav-btn {
  background: #fff;
  color: #6a82fb;
  border: none;
  border-radius: 18px;
  padding: 7px 18px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  box-shadow: 0 1.5px 6px 0 #fc5c7d11;
  transition: background 0.18s, color 0.18s;
}
.nav-btn:hover {
  background: #fc5c7d;
  color: #fff;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.18s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
