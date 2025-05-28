<template>
  <div class="stat-flex-item">
    <el-card shadow="never" class="airline-stat-card">
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

// 获取并渲染数据
const renderChart = async () => {
  const res = await airportApi.getTopFiveAirlines()
  // 兼容响应拦截器直接返回data数组
  const data = Array.isArray(res) ? res : (res?.data || [])
  // 按订单数降序排序
  const sorted = [...data].sort((a, b) => Number(b.airlineOrderCount) - Number(a.airlineOrderCount))
  // 构造x轴和y轴数据
  const xData = sorted.map(item => `${item.airlineDepartureCity}-${item.airlineArrivalCity}\n航班编号:${item.airlineId}`)
  const yData = sorted.map(item => Number(item.airlineOrderCount))

  if (!chartInstance) {
    chartInstance = echarts.init(chartRef.value)
  }
  const option = {
    title: {
      text: '前五航班订单量统计',
      left: 'center',
      top: 10,
      textStyle: { fontSize: 18 }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }
    },
    grid: { left: 40, right: 30, bottom: 60, top: 60 },
    xAxis: {
      type: 'category',
      data: xData,
      axisLabel: {
        interval: 0,
        fontSize: 13,
        formatter: val => val.replace('\\n', '\n')
      }
    },
    yAxis: {
      type: 'value',
      name: '订单数',
      minInterval: 1
    },
    series: [
      {
        name: '订单数',
        type: 'bar',
        data: yData,
        itemStyle: {
          color: '#6a82fb',
          borderRadius: [6, 6, 0, 0]
        },
        barWidth: 40
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
.airline-stat-card {
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