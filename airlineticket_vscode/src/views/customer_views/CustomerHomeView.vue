<template>
  <div class="customer-home">
    <CustomerNavBar />
    <div class="project-title-box">
      <div class="project-logo-bg"></div>
      <h1>航空订票系统</h1>
      <div class="project-desc">
        <el-icon style="color:#00e6e6;font-size:1.5em;">
          <svg width="1.3em" height="1.3em" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M2.5 19.5L21.5 12.5M2.5 19.5L7.5 14.5M2.5 19.5L9.5 11.5M21.5 12.5L14.5 4.5M21.5 12.5L17.5 19.5" stroke="#00e6e6" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg>
        </el-icon>
        <span>让出行更简单 · 更智能 · 更贴心</span>
      </div>
    </div>
    <div class="chat-box ai-card-style">
      <div class="chat-header chat-header-center">
        <span class="chat-icon">🤖</span>
        <span class="chat-title">AI 订票助手</span>
      </div>
      <chatWindows />
    </div>
    <footer class="footer-signature">
      <el-icon style="vertical-align:middle;"><i-ep-UserFilled /></el-icon>
      <span>© 2025 航空订票系统 | 作者：张家圣、杨健铭、吴仙勇</span>
    </footer>
  </div>
</template>

<script setup>
import chatWindows from '@/components/ChatWindow.vue'
import CustomerNavBar from '@/components/CustomerNavBar.vue'
import { ref } from 'vue'
import { ElIcon } from 'element-plus'
import { UserFilled } from '@element-plus/icons-vue'
</script>

<style scoped>
.customer-home {
  min-height: 100vh;
  /* 背景叠加：底层为图片，上层为蓝色渐变，提升风格统一 */
  background: linear-gradient(120deg, #6a9bffcc 0%, #377dffcc 100%), url('@/assets/background.png') center/cover no-repeat;
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
  margin: 28px 0 18px 0;
  padding: 18px 0 12px 0;
  background: linear-gradient(90deg, #ffe6fa 0%, #e6e6ff 100%);
  border-radius: 26px;
  box-shadow: 0 4px 32px #377dff33;
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
.project-logo-bg {
  width: 56px;
  height: 56px;
  margin-bottom: 10px;
  border-radius: 50%;
  background: url('@/assets/logo.svg') center/cover no-repeat, #fff;
  box-shadow: 0 2px 12px #6a82fb55;
  display: inline-block;
}
.project-title-box h1 {
  font-size: 2.2rem;
  margin: 0 0 10px 0;
  color: #377dff;
  letter-spacing: 6px;
  font-family: 'JetBrains Mono', 'Consolas', monospace;
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
.chat-box {
  width: 75vw;
  max-width: 1400px;
  margin: 32px 0 24px 0;
  background: rgba(255,255,255,0.97);
  border-radius: 22px;
  box-shadow: 0 2px 24px #377dff33;
  padding: 0 0 18px 0;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  min-height: 600px;
  border: 3px solid;
  border-image: linear-gradient(90deg, #6a9bff 0%, #377dff 60%, #fff 100%) 1;
  z-index: 1;
  max-height: 900px;
  overflow: hidden;
}

/* 保证 ChatWindow 填满 chat-box 并不超出 */
.chat-box > * {
  flex: 1 1 auto;
  min-width: 0;
  min-height: 0;
  /* 让子组件高度自适应且不撑出父容器 */
}
.ai-helper-header {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-size: 1.35rem;
  font-weight: 700;
  color: #377dff;
  letter-spacing: 1px;
  margin-top: 32px;
  margin-bottom: 0;
  animation: ai-bounce 1.2s infinite alternate;
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
.chat-header-center {
  justify-content: center;
  margin-top: 32px;
  margin-bottom: 0;
  font-size: 1.35rem;
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
  color: #bbc2cf;
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
    margin: 10px 0 8px 0;
    padding: 8px 0 6px 0;
    background: linear-gradient(90deg, #ffe6fa 0%, #e6e6ff 100%);
    width: 100vw;
    max-width: 100vw;
  }
  .project-title-box h1 {
    font-size: 1.3rem;
    margin-bottom: 6px;
    letter-spacing: 2px;
  }
  .project-logo-bg {
    width: 36px;
    height: 36px;
    margin-bottom: 6px;
  }
  .project-desc {
    font-size: 1.05rem;
    gap: 8px;
  }
}
</style>