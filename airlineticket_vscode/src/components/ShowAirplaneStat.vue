<template>
  <div class="stat-flex-item">
    <el-card shadow="never" class="airplane-stat-card">
      <div ref="chartRef" style="width: 100%; height: 400px;"></div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import * as airportApi from '@/api/airport'

const chartRef = ref(null)
let chartInstance = null

const renderChart = async () => {
  const res = await airportApi.getAllAirplanesStat()
  // 兼容响应拦截器直接返回data对象
  const stat = res && typeof res === 'object' ? res : (res?.data || {})
  const pieData = [
    { value: stat.inFlight || 0, name: '航行中' },
    { value: stat.onLand || 0, name: '已着陆' },
    { value: stat.maintaining || 0, name: '维护中' }
  ]
  if (!chartInstance) {
    chartInstance = echarts.init(chartRef.value)
  }
  const option = {
    title: {
      text: '机场所有飞机状态分布',
      left: 'center',
      top: 10,
      textStyle: { fontSize: 18 }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'horizontal',
      bottom: 10,
      data: ['航行中', '已着陆', '维护中']
    },
    series: [
      {
        name: '飞机状态',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 8,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: true,
          position: 'outside',
          formatter: '{b}\n{c} 架 ({d}%)',
          fontSize: 14
        },
        labelLine: {
          show: true
        },
        data: pieData
      }
    ]
  }
  chartInstance.setOption(option)
  chartInstance.resize()
}

onMounted(() => {
  nextTick(() => {
    renderChart()
    window.addEventListener('resize', resizeChart)
  })
})

function resizeChart() {
  if (chartInstance) chartInstance.resize()
}
</script>

<style scoped>
.stat-flex-item {
  flex: 1 1 0;
  min-width: 320px;
  max-width: 600px;
  display: flex;
  justify-content: center;
}
.airplane-stat-card {
  width: 100%;
  min-width: 0;
  max-width: 600px;
  margin: 0 12px;
  padding: 0 0 10px 0;
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 2px 12px #6a82fb11;
}
</style>