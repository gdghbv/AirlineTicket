<template>
  <div class="airport-home-root">
    <div class="welcome-section">
      <h2>欢迎来到机场管理中心</h2>
      <p>在这里你可以实时查看机场运营数据、航班动态、客户与订单统计等信息。</p>
    </div>
   
    <div class="stat-flex-row">
      <ShowAirlineStat />
      <ShowAirplaneStat />
    </div>
 <ShowAirportStat />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import ShowAirlineStat from '@/components/ShowAirlineStat.vue'
import ShowAirplaneStat from '@/components/ShowAirplaneStat.vue'
import ShowAirportStat from '@/components/ShowAirportStat.vue'

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
.welcome-section {
  text-align: center;
  margin: 32px 0 18px 0;
  transform: translateX(-100px);
}
.welcome-section h2 {
  font-size: 2.1rem;
  color: #4b5ae2;
  margin-bottom: 8px;
}
.welcome-section p {
  color: #666;
  font-size: 1.1rem;
}
.stat-flex-row {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: flex-start;
  gap: 0;
  margin-bottom: 24px;
  margin-left: -180px;
}
.desc-section {
  margin: 0 auto 24px auto;
  max-width: 700px;
}
.desc-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #4b5ae2;
  margin-bottom: 10px;
}
.desc-list {
  color: #555;
  font-size: 1rem;
  padding-left: 18px;
  margin: 0;
}
.desc-list li {
  margin-bottom: 6px;
}
@media (max-width: 1300px) {
  .stat-flex-row {
    flex-direction: column;
    align-items: center;
    margin-left: 0;
  }
}
</style>