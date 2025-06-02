<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu
          :default-active="activeMenu"
          class="el-menu-vertical-demo"
          background-color="#f5f7fa"
          text-color="#333"
          active-text-color="#409EFF"
          router
        >
          <el-menu-item index="airportHome">
            <el-icon><i-ep-house /></el-icon>
            <span>系统主页</span>
          </el-menu-item>
          <el-sub-menu index="airport-manage">
            <template #title>
              <el-icon><i-ep-menu /></el-icon>
              <span>机场管理</span>
            </template>
            <el-menu-item index="airportAirline">航班管理</el-menu-item>
            <el-menu-item index="airportAirplane">飞机管理</el-menu-item>
            <el-menu-item index="airportOrder">订单管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="user-manage">
            <template #title>
              <el-icon><i-ep-user /></el-icon>
              <span>用户信息管理</span>
            </template>
            <el-menu-item index="airportCenter">机场用户管理</el-menu-item>
            <el-menu-item index="airportCustomerManage">客户管理</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="header-bar">
            <span class="header-title">机场管理系统</span>
            <el-dropdown>
              <span class="el-dropdown-link">
                <el-icon><i-ep-user /></el-icon>
                账号
                <el-icon style="margin-left:2px;"><i-ep-arrow-down /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="handleLogout">
                    <el-icon><i-ep-switch-button /></el-icon>
                    退出登录
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
     
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { House, Menu, User } from '@element-plus/icons-vue'
import { useUserInfoStore } from '@/stores/userInfo'

const route = useRoute()
const router = useRouter()
const activeMenu = ref(route.path)
const userInfoStore = useUserInfoStore()

function handleLogout() {
  userInfoStore.initUserInfo()
  router.replace('/login')
}
</script>

<style scoped>

.el-aside {
  background: #f5f7fa;
  min-height: 100vh;
  box-shadow: 2px 0 8px #e0e7ef33;
}

.header-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 32px;
  background: #fff;
  box-shadow: 0 2px 8px 0 rgba(58,122,254,0.06);
}
.header-title {
  font-size: 1.3rem;
  color: #3a7afe;
  font-weight: bold;
  letter-spacing: 2px;
}
.el-dropdown-link {
  cursor: pointer;
  color: #3a4664;
  font-size: 16px;
  display: flex;
  align-items: center;
}
.el-main {
  background: #f7fafc;
  min-height: 100vh;
  padding: 0;
}
</style>