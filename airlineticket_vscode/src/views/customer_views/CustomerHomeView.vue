<template>
  <div class="customer-home">
    <CustomerNavBar />
    <div class="project-title-box">
      <img src="/favicon.ico" class="project-logo" alt="logo" />
      <h1>航空订票系统</h1>
      <div class="project-desc">
        <el-icon style="color:#00e6e6;font-size:1.5em;">
          <svg width="1.3em" height="1.3em" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M2.5 19.5L21.5 12.5M2.5 19.5L7.5 14.5M2.5 19.5L9.5 11.5M21.5 12.5L14.5 4.5M21.5 12.5L17.5 19.5" stroke="#00e6e6" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg>
        </el-icon>
        <span>让出行更简单 · 更智能 · 更贴心</span>
      </div>
    </div>
    <div class="carousel-box">
      <el-carousel height="260px" indicator-position="outside" arrow="always" :interval="3500">
        <el-carousel-item v-for="(img, idx) in images" :key="idx">
          <img :src="img" class="carousel-img" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="chat-box ai-card-style">
      <div class="chat-header">
        <span class="chat-icon">🤖</span>
        <span class="chat-title">AI 订票助手</span>
      </div>
      <chatWindows />
    </div>
    <footer class="footer-signature">
      <el-icon style="vertical-align:middle;color:#00e6e6;"><i-ep-UserFilled /></el-icon>
      <span>© 2025 航空订票系统 | 作者：YourName</span>
    </footer>
  </div>
</template>

<script setup>
import chatWindows from '@/components/ChatWindow.vue'
import CustomerNavBar from '@/components/CustomerNavBar.vue'
import { ref } from 'vue'
import { ElIcon } from 'element-plus'
import { UserFilled } from '@element-plus/icons-vue'

const images = ref([
  'https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80',
  'https://images.unsplash.com/photo-1464037866556-6812c9d1c72e?auto=format&fit=crop&w=800&q=80',
  'https://images.unsplash.com/photo-1519125323398-675f0ddb6308?auto=format&fit=crop&w=800&q=80',
])
</script>

<style scoped>
.customer-home {
  min-height: 100vh;
  background: linear-gradient(120deg, #6a9bff 0%, #377dff 100%);
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-x: hidden;
}
/* 白云装饰 */
.customer-home::before, .customer-home::after {
  content: '';
  position: absolute;
  z-index: 0;
  pointer-events: none;
  border-radius: 50%;
  opacity: 0.18;
  background: #fff;
}
.customer-home::before {
  width: 340px;
  height: 120px;
  left: 8vw;
  top: 60px;
  filter: blur(8px);
}
.customer-home::after {
  width: 420px;
  height: 160px;
  right: 6vw;
  top: 120px;
  filter: blur(12px);
}
.project-title-box {
  margin: 64px 0 32px 0;
  background: linear-gradient(90deg, #e6fff6 0%, #d2ffe6 100%);
  border-radius: 26px;
  box-shadow: 0 4px 32px #377dff33;
  padding: 38px 0 30px 0;
  text-align: center;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100vw;
  max-width: 100vw;
  min-width: 0;
  left: 50%;
  right: 50%;
  transform: translateX(-50%);
  border: 3px solid;
  border-image: linear-gradient(90deg, #6a9bff 0%, #377dff 60%, #fff 100%) 1;
  z-index: 1;
}
.project-logo {
  width: 72px;
  height: 72px;
  margin-bottom: 12px;
  filter: drop-shadow(0 2px 12px #6a82fb55);
}
.project-title-box h1 {
  font-size: 3.2rem;
  color: #377dff;
  letter-spacing: 6px;
  font-family: 'JetBrains Mono', 'Consolas', monospace;
  margin: 0 0 14px 0;
  text-shadow: 0 2px 24px #377dff22;
  font-weight: 900;
}
.project-desc {
  color: #377dff;
  font-size: 1.35rem;
  display: flex;
  align-items: center;
  gap: 16px;
  justify-content: center;
  margin-top: 6px;
  text-shadow: 0 2px 12px #377dff11;
}
.carousel-box {
  width: 90vw;
  max-width: 1200px;
  margin: 18px 0 32px 0;
  border-radius: 22px;
  overflow: hidden;
  box-shadow: 0 2px 24px #377dff33;
  background: rgba(255,255,255,0.97);
  border: 3px solid;
  border-image: linear-gradient(90deg, #6a9bff 0%, #377dff 60%, #fff 100%) 1;
  z-index: 1;
}
.carousel-img {
  width: 100%;
  height: 260px;
  object-fit: cover;
  border-radius: 18px;
  transition: transform 0.7s cubic-bezier(.4,2,.6,1.2);
  box-shadow: 0 2px 16px #377dff22;
}
.el-carousel__item.is-active .carousel-img {
  transform: scale(1.04);
}
.chat-box {
  width: 90vw;
  max-width: 1100px;
  margin: 32px 0 24px 0;
  background: rgba(255,255,255,0.97);
  border-radius: 22px;
  box-shadow: 0 2px 24px #377dff33;
  padding: 0 0 18px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 340px;
  border: 3px solid;
  border-image: linear-gradient(90deg, #6a9bff 0%, #377dff 60%, #fff 100%) 1;
  z-index: 1;
}
.chat-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  font-size: 1.18rem;
  font-weight: 700;
  color: #377dff;
  letter-spacing: 1px;
  margin-top: 18px;
}
.chat-icon {
  font-size: 1.7rem;
  animation: ai-bounce 1.2s infinite alternate;
}
@keyframes ai-bounce {
  0% { transform: translateY(0); }
  100% { transform: translateY(-6px) scale(1.08); }
}
.footer-signature {
  margin-top: auto;
  padding: 18px 0 12px 0;
  color: #377dff;
  font-size: 15px;
  letter-spacing: 2px;
  text-align: center;
  width: 100%;
  background: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}
@media (max-width: 900px) {
  .carousel-box, .chat-box {
    width: 98vw;
    min-width: 0;
    max-width: 100vw;
  }
  .project-title-box {
    width: 100vw;
    max-width: 100vw;
    padding: 18px 0 14px 0;
  }
  .project-title-box h1 {
    font-size: 2.1rem;
    letter-spacing: 2px;
  }
  .project-logo {
    width: 48px;
    height: 48px;
  }
  .project-desc {
    font-size: 1.05rem;
    gap: 8px;
  }
}
</style>