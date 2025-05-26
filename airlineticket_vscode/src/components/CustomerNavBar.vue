<template>
  <nav class="navbar">
    <div class="navbar-left">
      <router-link to="/customerHome" class="navbar-logo">
        <img src="@/assets/logo.svg" alt="logo" />
        <span class="navbar-title">航空订票系统</span>
      </router-link>
      <div class="navbar-links">
        <router-link to="/customerHome" class="navbar-link" active-class="active">首页</router-link>
        <router-link to="/customerOrder" class="navbar-link" active-class="active">订购机票</router-link>
        <router-link to="/customerCenter" class="navbar-link" active-class="active">个人中心</router-link>
      </div>
    </div>
    <div class="navbar-right">
      <template v-if="isLogin">
        <el-button type="primary" @click="logout" class="logout-btn">退出登录</el-button>
      </template>
      <template v-else>
        <router-link to="/login" class="navbar-auth-link">登录</router-link>
        <span class="navbar-auth-divider">|</span>
        <router-link to="/register" class="navbar-auth-link">注册</router-link>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { useUserInfoStore } from '@/stores/userInfo'
import { ElMessage } from 'element-plus'
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const userInfoStore = useUserInfoStore()
const isLogin = computed(() => !!userInfoStore.token)

const logout = () => {
  userInfoStore.initUserInfo()
  ElMessage.success('已退出登录')
  window.location.reload()
}
</script>

<style scoped>
/* 导航栏样式，与登录页面保持一致 */
.navbar {
  width: 100vw;
  height: 70px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(18px) saturate(180%);
  box-shadow: 0 8px 32px 0 #6a82fb22, 0 1.5px 6px 0 #fc5c7d11;
  border: none;
  border-bottom: 1px solid rgba(106, 130, 251, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 40px;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
  font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
  animation: navbar-slide-down 0.6s ease-out;
}

/* 导航栏滑入动画 */
@keyframes navbar-slide-down {
  0% {
    opacity: 0;
    transform: translateY(-70px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

.navbar-left {
  display: flex;
  align-items: center;
}

.navbar-logo {
  display: flex;
  align-items: center;
  text-decoration: none;
  margin-right: 40px;
  transition: transform 0.2s ease;
}

.navbar-logo:hover {
  transform: scale(1.05);
}

.navbar-logo img {
  width: 40px;
  height: 40px;
  margin-right: 12px;
  filter: drop-shadow(0 2px 8px rgba(106, 130, 251, 0.3));
}

.navbar-title {
  font-size: 22px;
  font-weight: 700;
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  letter-spacing: 1px;
}

.navbar-links {
  display: flex;
  align-items: center;
  gap: 32px;
  justify-content: center;
  flex: 1;
}

.navbar-link {
  color: #6a82fb;
  font-size: 16px;
  font-weight: 600;
  text-decoration: none;
  padding: 10px 20px;
  border-radius: 14px;
  position: relative;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(106, 130, 251, 0.2);
}

.navbar-link::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  border-radius: 14px;
  opacity: 0;
  transition: opacity 0.3s ease;
  z-index: -1;
}

.navbar-link.active,
.navbar-link:hover {
  color: #fff;
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(106, 130, 251, 0.4);
}

.navbar-link.active::before,
.navbar-link:hover::before {
  opacity: 1;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

/* Element Plus 按钮样式覆盖 */
:deep(.logout-btn) {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%) !important;
  border: none !important;
  border-radius: 14px !important;
  font-weight: 600 !important;
  padding: 10px 20px !important;
  box-shadow: 0 2px 8px rgba(106, 130, 251, 0.3) !important;
  transition: all 0.2s ease !important;
}

:deep(.logout-btn:hover) {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%) !important;
  transform: translateY(-2px) !important;
  box-shadow: 0 4px 16px rgba(252, 92, 125, 0.4) !important;
}

.navbar-auth-link {
  color: #6a82fb;
  font-size: 16px;
  font-weight: 600;
  text-decoration: none;
  padding: 8px 16px;
  border-radius: 12px;
  margin: 0 4px;
  transition: all 0.2s ease;
  background: rgba(106, 130, 251, 0.1);
  border: 1px solid rgba(106, 130, 251, 0.2);
}

.navbar-auth-link:hover {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  color: #fff;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(106, 130, 251, 0.3);
}

.navbar-auth-divider {
  color: rgba(106, 130, 251, 0.4);
  font-size: 16px;
  font-weight: 300;
  margin: 0 8px;
}

/* 响应式设计 */
@media (max-width: 900px) {
  .navbar {
    padding: 0 20px;
    height: 60px;
  }
  
  .navbar-title {
    font-size: 18px;
  }
  
  .navbar-links {
    gap: 16px;
  }
  
  .navbar-link {
    padding: 8px 12px;
    font-size: 14px;
  }
  
  .navbar-logo img {
    width: 32px;
    height: 32px;
  }
}

@media (max-width: 600px) {
  .navbar-links {
    display: none;
  }
  
  .navbar-title {
    font-size: 16px;
  }
}
</style>
