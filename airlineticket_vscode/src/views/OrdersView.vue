<template>
  <NavBar />
  <div class="orders-root fade-in-page">
    <h2 class="orders-title">订单查询</h2>
    <div class="orders-placeholder">[ 这里是订单查询页面占位 ]</div>
  </div>
</template>
<style scoped>
.orders-root {
  min-height: 60vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding: 48px 0;
}
.fade-in-page {
  animation: fade-in-page 0.8s cubic-bezier(.68,-0.55,.27,1.55);
}
@keyframes fade-in-page {
  0% { opacity: 0; transform: translateY(40px) scale(0.98); }
  100% { opacity: 1; transform: translateY(0) scale(1); }
}
.orders-title {
  font-size: 2rem;
  font-weight: 700;
  color: #43e97b;
  margin-bottom: 28px;
  letter-spacing: 2px;
  text-shadow: 0 2px 12px #43e97b22;
  animation: fade-in-title 0.7s;
}
@keyframes fade-in-title {
  0% { opacity: 0; transform: translateY(20px); }
  100% { opacity: 1; transform: translateY(0); }
}
.orders-placeholder {
  background: rgba(255,255,255,0.97);
  border-radius: 16px;
  box-shadow: 0 4px 24px 0 #43e97b22, 0 1.5px 6px 0 #6a82fb11;
  padding: 48px;
  color: #43e97b;
  font-size: 1.2rem;
  text-align: center;
  margin-top: 24px;
}
</style>
