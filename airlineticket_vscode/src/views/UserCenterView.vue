<template>
  <NavBar />
  <div class="user-center-root fade-in-page">
    <div class="user-center-main card-pop">
      <aside class="user-side-bar">
        <div :class="['side-item', tab==='info' ? 'active' : '']" @click="tab='info'">
          <span class="side-icon">👤</span> 个人信息
        </div>
        <div :class="['side-item', tab==='orders' ? 'active' : '']" @click="tab='orders'">
          <span class="side-icon">📄</span> 历史订单
        </div>
      </aside>
      <section class="user-content">
        <transition name="fade-slide" mode="out-in">
          <div v-if="tab==='info'" key="info" class="info-panel">
            <h2>个人信息</h2>
            <div class="info-row"><span>昵称：</span> <b>示例用户</b></div>
            <div class="info-row"><span>手机号：</span> <b>138****8888</b></div>
            <div class="info-row"><span>注册时间：</span> <b>2024-01-01</b></div>
            <!-- 可扩展更多信息 -->
          </div>
          <div v-else key="orders" class="orders-panel">
            <h2>历史订单</h2>
            <div class="order-list">
              <div class="order-item" v-for="i in 3" :key="i">
                <div>订单号：20240{{i}}</div>
                <div>航班：北京 → 上海</div>
                <div>时间：2024-04-0{{i}} 14:00</div>
                <div>状态：已完成</div>
              </div>
            </div>
          </div>
        </transition>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import NavBar from '@/components/NavBar.vue'
const tab = ref('info')
</script>

<style scoped>
.user-center-root {
  min-height: 100vh;
  background: linear-gradient(120deg, #f8fafc 0%, #e0e7ff 100%);
}
.user-center-main {
  display: flex;
  max-width: 1100px;
  margin: 36px auto 0 auto;
  background: rgba(255,255,255,0.97);
  border-radius: 28px;
  box-shadow: 0 8px 32px 0 #6a82fb22, 0 1.5px 6px 0 #fc5c7d11;
  min-height: 520px;
  padding: 0 0 0 0;
  overflow: hidden;
}
.user-side-bar {
  width: 180px;
  background: linear-gradient(135deg, #6a82fb 0%, #fc5c7d 100%);
  display: flex;
  flex-direction: column;
  align-items: stretch;
  padding: 36px 0 0 0;
  border-radius: 28px 0 0 28px;
  box-shadow: 2px 0 12px #6a82fb11;
}
.side-item {
  color: #fff;
  font-size: 1.1rem;
  font-weight: 500;
  padding: 16px 0 16px 38px;
  cursor: pointer;
  transition: background 0.18s, color 0.18s;
  display: flex;
  align-items: center;
  gap: 10px;
  border-left: 4px solid transparent;
}
.side-item.active, .side-item:hover {
  background: rgba(255,255,255,0.13);
  color: #fc5c7d;
  border-left: 4px solid #fc5c7d;
}
.side-icon {
  font-size: 1.3rem;
}
.user-content {
  flex: 1;
  padding: 48px 48px 32px 48px;
  min-height: 400px;
  background: transparent;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
.info-panel h2, .orders-panel h2 {
  font-size: 1.5rem;
  color: #6a82fb;
  margin-bottom: 24px;
  font-weight: 700;
}
.info-row {
  font-size: 1.1rem;
  margin-bottom: 18px;
  color: #333;
}
.order-list {
  display: flex;
  flex-direction: column;
  gap: 18px;
}
.order-item {
  background: linear-gradient(90deg, #f8fafc 60%, #e0e7ff 100%);
  border-radius: 14px;
  box-shadow: 0 2px 8px 0 #6a82fb11;
  padding: 18px 24px;
  color: #444;
  font-size: 1rem;
  transition: box-shadow 0.18s;
}
.order-item:hover {
  box-shadow: 0 4px 16px 0 #fc5c7d22;
}
.fade-slide-enter-active, .fade-slide-leave-active {
  transition: all 0.35s cubic-bezier(.68,-0.55,.27,1.55);
}
.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(30px);
}
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-30px);
}
.fade-in-page {
  animation: fade-in-page 0.8s cubic-bezier(.68,-0.55,.27,1.55);
}
@keyframes fade-in-page {
  0% { opacity: 0; transform: translateY(40px) scale(0.98); }
  100% { opacity: 1; transform: translateY(0) scale(1); }
}
.card-pop {
  animation: card-pop 0.7s cubic-bezier(.68,-0.55,.27,1.55);
}
@keyframes card-pop {
  0% { transform: scale(0.92) translateY(30px); opacity: 0; }
  100% { transform: scale(1) translateY(0); opacity: 1; }
}
@media (max-width: 900px) {
  .user-center-main { flex-direction: column; }
  .user-side-bar { flex-direction: row; width: 100%; border-radius: 28px 28px 0 0; padding: 0; }
  .side-item { padding: 16px 24px; border-left: none; border-top: 4px solid transparent; }
  .side-item.active, .side-item:hover { border-left: none; border-top: 4px solid #fc5c7d; }
  .user-content { padding: 32px 12px; }
}
</style>
