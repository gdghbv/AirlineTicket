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
        <!-- <el-header>Header</el-header> -->
        <el-main>
          <router-view />
        </el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { House, Menu, User } from '@element-plus/icons-vue'

const route = useRoute()
const activeMenu = ref(route.path)
</script>

<style scoped>
.common-layout {
  height: 100vh;
}
.el-aside {
  background: #f5f7fa;
  min-height: 100vh;
  box-shadow: 2px 0 8px #e0e7ef33;
}
.el-menu {
  border-right: none;
}
</style>