<template>
  <el-container style="height: 100vh; min-width: 100vw;">
    <el-header >
      <CustomerNavBar />
    </el-header>
    <el-container style="height:calc(100vh - 64px);">
      <el-aside width="220px" class="sidebar">
        <el-menu :default-active="activeMenu" @select="handleMenuSelect">
          <el-menu-item index="info">个人信息</el-menu-item>
          <el-menu-item index="orders">历史订单</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import CustomerNavBar from '@/components/CustomerNavBar.vue'

const router = useRouter()
const route = useRoute()
const activeMenu = ref('info')

watch(
  () => route.name,
  (name) => {
    if (name === 'customerCenterInfo') activeMenu.value = 'info'
    else if (name === 'customerCenterOrders') activeMenu.value = 'orders'
  },
  { immediate: true }
)

function handleMenuSelect(key) {
  if (key === 'info') router.push({ name: 'customerCenterInfo' })
  else if (key === 'orders') router.push({ name: 'customerCenterOrders' })
}
</script>

<style scoped>
.el-header {
  background: #fff;
  box-shadow: 0 2px 8px #0001;
  z-index: 10;
  padding: 0;
}
.el-container {
  min-height: 0;
  width: 100vw;
  background: #e3f2fd; /* 天蓝色背景 */
}
.sidebar {
  background: #e0e7ff;
  border-radius: 0 12px 12px 0;
  min-height: 100%;
  margin: 0;
  padding: 0;
}
.main-content {
  padding: 32px 24px;
  min-height: 100%;
  background: transparent;
}
</style>