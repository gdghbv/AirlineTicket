<template>
  <div class="airport-center">
    <div class="page-header">
      <h2>用户管理</h2>
    </div>

    <el-tabs v-model="activeTab" class="center-tabs">
      <el-tab-pane label="个人信息" name="personal">
        <AirportUser />
      </el-tab-pane>
      <el-tab-pane label="所有用户" name="all">
        <AirportUserAll />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import AirportUser from './AirportUser.vue'
import AirportUserAll from './AirportUserAll.vue'

const activeTab = ref('personal')
</script>

<style scoped>
.airport-center {
  padding: 24px;
  height: 100%;
  background: #f7fafc;
}

.page-header {
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #3a7afe;
  font-size: 2rem;
  letter-spacing: 1px;
  font-weight: 600;
}

.center-tabs {
  height: calc(100% - 80px);
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(58,122,254,0.08);
  padding: 16px 12px 0 12px;
}

:deep(.el-tabs__header) {
  background: #f0f4fa;
  border-radius: 8px 8px 0 0;
}

:deep(.el-tabs__item.is-active) {
  color: #3a7afe !important;
  font-weight: bold;
  background: #eaf3ff;
  border-radius: 8px 8px 0 0;
}

:deep(.el-tabs__item) {
  color: #6b7b8d;
  font-size: 16px;
  padding: 12px 28px;
}

:deep(.el-tabs__content) {
  height: calc(100% - 60px);
  background: #fff;
  border-radius: 0 0 12px 12px;
}

:deep(.el-tab-pane) {
  height: 100%;
}
</style>