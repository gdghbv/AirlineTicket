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
        <el-dropdown trigger="hover" @command="handleCommand">
          <span class="el-dropdown-link user-avatar">
            <el-icon><i-ep-user /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
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

const handleCommand = (command) => {
  if (command === 'logout') {
    userInfoStore.initUserInfo()
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}
</script>

<style scoped>
.navbar {
  width: 100vw;
  height: 60px;
  background: #fff;
  box-shadow: 0 2px 12px 0 #6a82fb11;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 36px;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
}

.navbar-left {
  display: flex;
  align-items: center;
}

.navbar-logo {
  display: flex;
  align-items: center;
  text-decoration: none;
  margin-right: 32px;
}

.navbar-logo img {
  width: 36px;
  height: 36px;
  margin-right: 10px;
}

.navbar-title {
  font-size: 20px;
  font-weight: 700;
  color: #1976d2;
  letter-spacing: 1px;
}

.navbar-links {
  display: flex;
  align-items: center;
  gap: 48px;
  justify-content: center;
  flex: 1;
}

.navbar-link {
  color: #333;
  font-size: 16px;
  text-decoration: none;
  padding: 6px 22px;
  border-radius: 8px;
  transition: background 0.2s, color 0.2s;
}

.navbar-link.active,
.navbar-link:hover {
  background: linear-gradient(90deg, #43e97b 0%, #6a82fb 100%);
  color: #fff;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 18px;
}

.user-avatar {
  display: flex;
  align-items: center;
  font-size: 26px;
  color: #1976d2;
  cursor: pointer;
  border-radius: 50%;
  background: #f5f7fa;
  padding: 6px 10px;
  transition: background 0.2s;
}

.user-avatar:hover {
  background: #e0e7ff;
}

.navbar-auth-link {
  color: #1976d2;
  font-size: 16px;
  text-decoration: none;
  margin: 0 4px;
  transition: color 0.2s;
}

.navbar-auth-link:hover {
  color: #43e97b;
}

.navbar-auth-divider {
  color: #b0b8c9;
  font-size: 16px;
  margin: 0 2px;
}
</style>
