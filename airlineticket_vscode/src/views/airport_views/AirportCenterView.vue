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
  padding: 20px;
  height: 100%;
}

.page-header {
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #333;
}

.center-tabs {
  height: calc(100% - 80px);
}

:deep(.el-tabs__content) {
  height: calc(100% - 60px);
}

:deep(.el-tab-pane) {
  height: 100%;
}
</style>