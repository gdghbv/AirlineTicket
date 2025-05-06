<template>
  <NavBar />
  <div class="login-root fade-in-page">
    <div class="login-card card-pop">
      <div class="login-left">
        <div class="login-graphic animated-slide-in">
          <svg width="120" height="120" viewBox="0 0 120 120">
            <defs>
              <linearGradient id="lg-login" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#6a82fb"/>
                <stop offset="100%" stop-color="#fc5c7d"/>
              </linearGradient>
            </defs>
            <circle cx="60" cy="60" r="55" fill="url(#lg-login)" opacity="0.7"/>
            <circle cx="60" cy="60" r="40" fill="#fff" opacity="0.15"/>
            <circle cx="60" cy="60" r="25" fill="#fff" opacity="0.08"/>
          </svg>
          <div class="login-title">欢迎登录</div>
          <div class="login-desc">智能出行，从这里开始</div>
        </div>
      </div>
      <div class="login-right animated-slide-in-right">
        <form class="login-form" @submit.prevent="handleLogin">
          <div class="form-title">登录</div>
          <div class="form-group role-group">
            <label>登录身份</label>
            <div class="role-switch">
              <label :class="['role-option', role === 'customer' ? 'active' : '']">
                <input type="radio" value="customer" v-model="role" />
                <span class="role-label">客户登录</span>
              </label>
              <label :class="['role-option', role === 'airport' ? 'active' : '']">
                <input type="radio" value="airport" v-model="role" />
                <span class="role-label">机场登录</span>
              </label>
            </div>
          </div>
          <div class="form-group">
            <label>手机号</label>
            <input v-model="phone" type="tel" required pattern="^1[3-9]\\d{9}$" placeholder="请输入手机号" />
          </div>
          <div class="form-group">
            <label>密码</label>
            <input v-model="password" type="password" required placeholder="请输入密码" />
          </div>
          <button class="login-btn" type="submit">登录</button>
          <div class="to-register">没有账号？<router-link to="/register">注册</router-link></div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import NavBar from '@/components/NavBar.vue'
const phone = ref('')
const password = ref('')
const role = ref('customer')
const router = useRouter()
function handleLogin() {
  // 登录逻辑
  router.push('/')
}
</script>

<style scoped>
.login-root {
  min-height: 100vh;
  background: linear-gradient(120deg, #f8fafc 0%, #e0e7ff 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-card {
  display: flex;
  width: 720px;
  min-height: 420px;
  border-radius: 32px;
  box-shadow: 0 8px 32px 0 #43e97b22, 0 1.5px 6px 0 #38f9d711;
  background: rgba(255,255,255,0.97);
  overflow: hidden;
  backdrop-filter: blur(18px) saturate(180%);
  animation: card-pop 0.8s cubic-bezier(.68,-0.55,.27,1.55);
}
.login-left {
  flex: 1.1;
  background: linear-gradient(135deg, #6a82fb 0%, #fc5c7d 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
  position: relative;
  z-index: 1;
}
.login-graphic {
  display: flex;
  flex-direction: column;
  align-items: center;
  animation: fade-in 1.2s;
}
.login-title {
  font-size: 2rem;
  font-weight: 700;
  margin-top: 18px;
  letter-spacing: 2px;
  text-shadow: 0 2px 12px #6a82fb33;
}
.login-desc {
  font-size: 1.1rem;
  margin-top: 10px;
  opacity: 0.85;
  letter-spacing: 1px;
}
.login-right {
  flex: 1.2;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255,255,255,0.92);
  position: relative;
  z-index: 2;
  animation: fade-in-right 1.2s;
}
@keyframes fade-in {
  0% { opacity: 0; transform: translateY(40px); }
  100% { opacity: 1; transform: translateY(0); }
}
@keyframes fade-in-right {
  0% { opacity: 0; transform: translateX(40px); }
  100% { opacity: 1; transform: translateX(0); }
}
.animated-fade-in { animation: fade-in 1.2s; }
.animated-slide-in { animation: fade-in 1.2s; }
.animated-slide-in-right { animation: fade-in-right 1.2s; }
.animated-bounce-in { animation: bounce-in 0.7s; }
@keyframes bounce-in {
  0% { transform: scale(0.8); opacity: 0; }
  60% { transform: scale(1.05); opacity: 1; }
  100% { transform: scale(1); }
}
.login-form {
  width: 90%;
  max-width: 320px;
  display: flex;
  flex-direction: column;
  gap: 22px;
  background: transparent;
  padding: 24px 0 18px 0;
}
.form-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #6a82fb;
  text-align: center;
  margin-bottom: 10px;
}
.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}
label {
  font-size: 15px;
  color: #5a5a7a;
  font-weight: 500;
}
input {
  padding: 12px 14px;
  border: 1.5px solid #e3eaf7;
  border-radius: 14px;
  font-size: 16px;
  background: rgba(255,255,255,0.7);
  transition: border 0.2s, box-shadow 0.2s;
  outline: none;
  box-shadow: 0 1.5px 6px 0 rgba(0,0,0,0.03);
}
input:focus {
  border: 1.5px solid #6a82fb;
  background: #fff;
  box-shadow: 0 0 0 2px #6a82fb33;
}
.login-btn {
  width: 100%;
  padding: 13px 0;
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  color: #fff;
  border: none;
  border-radius: 14px;
  font-size: 18px;
  font-weight: 600;
  cursor: pointer;
  margin-top: 10px;
  box-shadow: 0 2px 8px #fc5c7d22;
  transition: background 0.2s, box-shadow 0.2s, transform 0.15s;
}
.login-btn:hover {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
  box-shadow: 0 4px 16px 0 #fc5c7d33;
  transform: translateY(-2px) scale(1.03);
}
.to-register {
  margin-top: 18px;
  text-align: right;
  font-size: 15px;
}
.to-register a {
  color: #fc5c7d;
  text-decoration: underline;
  margin-left: 4px;
  font-weight: 500;
}
.role-group {
  margin-bottom: 10px;
  margin-top: 0;
}
.role-switch {
  display: flex;
  gap: 18px;
  margin-top: 4px;
}
.role-option {
  display: flex;
  align-items: center;
  background: rgba(245,247,255,0.7);
  border-radius: 18px;
  padding: 7px 22px 7px 14px;
  cursor: pointer;
  font-size: 15px;
  font-weight: 500;
  color: #6a82fb;
  border: 1.5px solid transparent;
  transition: background 0.2s, border 0.2s, color 0.2s, box-shadow 0.2s;
  box-shadow: 0 1.5px 6px 0 rgba(106,130,251,0.04);
  position: relative;
  user-select: none;
}
.role-option input[type="radio"] {
  display: none;
}
.role-option.active {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  color: #fff;
  border: 1.5px solid #fc5c7d;
  box-shadow: 0 2px 8px 0 #fc5c7d22;
}
.role-option.active .role-label {
  font-weight: 700;
}
.role-option:hover:not(.active) {
  background: #e0e7ff;
  color: #fc5c7d;
  border: 1.5px solid #fc5c7d44;
}
.role-label {
  margin-left: 2px;
  letter-spacing: 1px;
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
</style>
