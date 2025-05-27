<template>
  <el-card shadow="never" class="airport-stat-card">
    <div class="stat-grid">
      <el-statistic
        title="系统客户数量"
        :value="stat.customerCount"
        prefix-icon="User"
        value-style="font-size: 2.2rem; color: #6a82fb;"
      />
      <el-statistic
        title="总订单数"
        :value="stat.orderCount"
        prefix-icon="Document"
        value-style="font-size: 2.2rem; color: #fc5c7d;"
      />
      <el-statistic
        title="已加入机场数"
        :value="stat.airportCount"
        prefix-icon="OfficeBuilding"
        value-style="font-size: 2.2rem; color: #67c23a;"
      />
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as airportApi from '@/api/airport'

const stat = ref({ customerCount: 0, orderCount: 0, airportCount: 0 })

const fetchStat = async () => {
  const res = await airportApi.getAirportUserAndOrderCount()
  if (res && typeof res === 'object') {
    stat.value = {
      customerCount: res.customerCount || 0,
      orderCount: res.orderCount || 0,
      airportCount: res.airportCount || 0
    }
  }
}

onMounted(() => {
  fetchStat()
})
</script>

<style scoped>
.airport-stat-card {
  width: 100%;
  min-width: 320px;
  margin: 0 auto 10px auto;
  padding: 18px 0 10px 0;
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 2px 12px #6a82fb11;
}
.stat-grid {
  display: flex;
  justify-content: space-around;
  align-items: center;
  gap: 32px;
}
@media (max-width: 700px) {
  .stat-grid {
    flex-direction: column;
    gap: 18px;
  }
}
</style>