<template>
  <NavBar />
  <main class="home-root fade-in-page">
    <section class="hero-section">
      <div class="hero-bg"></div>
      <div class="hero-content">
        <h1 class="hero-title">AI 智能订票平台</h1>
        <p class="hero-desc">让出行更简单 · 更智能 · 更贴心</p>
        <div class="hero-actions">
          <button class="hero-btn" @click="$router.push('/order')">立即订票</button>
          <button class="hero-btn secondary" @click="$router.push('/user')">个人中心</button>
        </div>
      </div>
    </section>
    <section class="ai-chat-section">
      <ChatWindow />
    </section>
    <footer class="main-footer">
      <span>© 2025 订票助手 AI · 智慧出行</span>
    </footer>
  </main>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue'
import ChatWindow from '@/components/ChatWindow.vue'
</script>

<style scoped>
.home-root {
  min-height: 100vh;
  background: linear-gradient(120deg, #f8fafc 0%, #e0e7ff 100%);
  font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
  position: relative;
  animation: fade-in-page 0.8s cubic-bezier(.68,-0.55,.27,1.55);
  display: flex;
  flex-direction: column;
}
@keyframes fade-in-page {
  0% { opacity: 0; transform: translateY(40px) scale(0.98); }
  100% { opacity: 1; transform: translateY(0) scale(1); }
}
.hero-section {
  position: relative;
  min-height: 320px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  margin-bottom: 24px;
}
.hero-bg {
  position: absolute;
  inset: 0;
  background: linear-gradient(100deg, #6a82fb 0%, #43e97b 100%);
  opacity: 0.13;
  z-index: 1;
  animation: hero-bg-float 8s infinite alternate ease-in-out;
}
@keyframes hero-bg-float {
  0% { filter: blur(0px); }
  100% { filter: blur(8px); }
}
.hero-content {
  position: relative;
  z-index: 2;
  text-align: center;
  padding: 40px 0 28px 0;
  width: 100%;
}
.hero-title {
  font-size: 2.2rem;
  font-weight: 800;
  color: #6a82fb;
  letter-spacing: 2px;
  margin-bottom: 10px;
  text-shadow: 0 2px 12px #6a82fb22;
  animation: fade-in-title 1s;
}
@keyframes fade-in-title {
  0% { opacity: 0; transform: translateY(-30px); }
  100% { opacity: 1; transform: translateY(0); }
}
.hero-desc {
  font-size: 1.1rem;
  color: #333;
  opacity: 0.85;
  margin-bottom: 22px;
  letter-spacing: 1px;
  animation: fade-in-desc 1.2s;
}
@keyframes fade-in-desc {
  0% { opacity: 0; transform: translateY(20px); }
  100% { opacity: 1; transform: translateY(0); }
}
.hero-actions {
  display: flex;
  gap: 18px;
  justify-content: center;
  margin-top: 8px;
}
.hero-btn {
  padding: 11px 32px;
  font-size: 1rem;
  font-weight: 600;
  border-radius: 16px;
  border: none;
  background: linear-gradient(90deg, #6a82fb 0%, #43e97b 100%);
  color: #fff;
  box-shadow: 0 2px 8px #6a82fb22;
  cursor: pointer;
  transition: background 0.2s, box-shadow 0.2s, transform 0.15s;
  animation: bounce-in 0.8s;
}
.hero-btn.secondary {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
}
.hero-btn:hover {
  background: linear-gradient(90deg, #43e97b 0%, #6a82fb 100%);
  box-shadow: 0 4px 16px 0 #43e97b33;
  transform: translateY(-2px) scale(1.03);
}
.hero-btn.secondary:hover {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  box-shadow: 0 4px 16px 0 #fc5c7d33;
}
@keyframes bounce-in {
  0% { transform: scale(0.8); opacity: 0; }
  60% { transform: scale(1.05); opacity: 1; }
  100% { transform: scale(1); }
}
.ai-chat-section {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  margin: 0 auto 32px auto;
  width: 100%;
  max-width: 520px;
}
.main-footer {
  text-align: center;
  color: #aaa;
  font-size: 1rem;
  margin: 32px 0 0 0;
  padding-bottom: 18px;
  letter-spacing: 1px;
}
@media (max-width: 700px) {
  .hero-title { font-size: 1.3rem; }
  .hero-content { padding: 18px 0 10px 0; }
  .ai-chat-section { max-width: 98vw; padding: 0 2vw; }
}
</style>
