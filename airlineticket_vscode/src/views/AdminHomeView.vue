<template>
  <div class="admin-home-root">
    <div class="admin-header">
      <div class="admin-title">机场管理系统首页</div>
      <div class="admin-desc">数据总览 · 智慧机场</div>
    </div>
    <div class="admin-main">
      <div class="admin-card">
        <div class="card-title">机票订购趋势</div>
        <div class="chart-placeholder">[ 这里放置订购趋势图表 ]</div>
      </div>
      <div class="admin-card">
        <div class="card-title">目的地分布</div>
        <div class="chart-placeholder">[ 这里放置目的地分布图表 ]</div>
      </div>
      <div class="admin-card">
        <div class="card-title">起点分布</div>
        <div class="chart-placeholder">[ 这里放置起点分布图表 ]</div>
      </div>
    </div>
  </div>
</template>

<script setup>
// 后续可引入图表库如 ECharts/Chart.js
</script>

<style scoped>
.admin-home-root {
  min-height: 100vh;
  background: linear-gradient(120deg, #e0e7ff 0%, #f8fafc 100%);
  padding: 0 0 48px 0;
}
.admin-header {
  width: 100%;
  background: linear-gradient(90deg, #11998e 0%, #38ef7d 100%);
  color: #fff;
  padding: 48px 0 32px 0;
  text-align: center;
  border-radius: 0 0 32px 32px;
  box-shadow: 0 4px 24px 0 #11998e22;
  margin-bottom: 36px;
}
.admin-title {
  font-size: 2.2rem;
  font-weight: 800;
  letter-spacing: 2px;
  margin-bottom: 10px;
  text-shadow: 0 2px 12px #11998e33;
}
.admin-desc {
  font-size: 1.2rem;
  opacity: 0.92;
  letter-spacing: 1px;
}
.admin-main {
  max-width: 1100px;
  margin: 0 auto;
  display: flex;
  gap: 32px;
  justify-content: center;
  flex-wrap: wrap;
}
.admin-card {
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 2px 12px 0 #11998e11;
  padding: 32px 28px 24px 28px;
  min-width: 320px;
  flex: 1 1 320px;
  max-width: 340px;
  margin-bottom: 18px;
  display: flex;
  flex-direction: column;
  align-items: center;
  animation: fade-in 1.2s;
}
.card-title {
  font-size: 1.2rem;
  font-weight: 700;
  color: #11998e;
  margin-bottom: 18px;
  letter-spacing: 1px;
}
.chart-placeholder {
  width: 220px;
  height: 120px;
  background: linear-gradient(120deg, #e0e7ff 60%, #f8fafc 100%);
  border-radius: 12px;
  color: #bbb;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  font-style: italic;
  box-shadow: 0 1.5px 6px 0 #11998e11;
}
@keyframes fade-in {
  0% { opacity: 0; transform: translateY(40px); }
  100% { opacity: 1; transform: translateY(0); }
}
@media (max-width: 900px) {
  .admin-main { flex-direction: column; gap: 18px; align-items: center; }
  .admin-card { width: 90vw; min-width: 0; max-width: 98vw; }
}
</style>
