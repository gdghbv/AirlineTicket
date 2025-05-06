<template>
  <NavBar />
  <div class="register-root fade-in-page">
    <div class="register-card card-pop">
      <div class="register-left">
        <div class="register-graphic animated-slide-in">
          <svg width="120" height="120" viewBox="0 0 120 120">
            <defs>
              <linearGradient id="lg2" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#43e97b"/>
                <stop offset="100%" stop-color="#38f9d7"/>
              </linearGradient>
            </defs>
            <circle cx="60" cy="60" r="55" fill="url(#lg2)" opacity="0.7"/>
            <circle cx="60" cy="60" r="40" fill="#fff" opacity="0.15"/>
            <circle cx="60" cy="60" r="25" fill="#fff" opacity="0.08"/>
          </svg>
          <div class="register-title">欢迎注册</div>
          <div class="register-desc">开启智能出行新体验</div>
        </div>
      </div>
      <div class="register-right animated-slide-in-right">
        <form class="register-form" @submit.prevent="handleRegister">
          <div class="form-title">创建账号</div>
          <transition name="input-focus">
            <div class="form-group role-group">
              <label>注册身份</label>
              <div class="role-switch">
                <label :class="['role-option', role === 'customer' ? 'active' : '']">
                  <input type="radio" value="customer" v-model="role" />
                  <span class="role-label">客户注册</span>
                </label>
                <label :class="['role-option', role === 'airport' ? 'active' : '']">
                  <input type="radio" value="airport" v-model="role" />
                  <span class="role-label">机场注册</span>
                </label>
              </div>
            </div>
          </transition>
          <transition name="input-focus">
            <div class="form-group" key="phone">
              <label>手机号</label>
              <input v-model="phone" type="tel" required pattern="^1[3-9]\d{9}$" placeholder="请输入手机号" />
            </div>
          </transition>
          <transition name="input-focus">
            <div class="form-group" key="password">
              <label>密码</label>
              <input v-model="password" type="password" required placeholder="请输入密码" />
            </div>
          </transition>
          <transition name="input-focus">
            <div class="form-group" key="confirm">
              <label>确认密码</label>
              <input v-model="confirm" type="password" required placeholder="请再次输入密码" />
            </div>
          </transition>
          <button class="register-btn animated-bounce-in" type="submit">
            <span class="btn-icon">✨</span> 注册
          </button>
          <div class="to-login">已有账号？<router-link to="/login">登录</router-link></div>
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
const confirm = ref('')
const role = ref('customer')
const router = useRouter()
function handleRegister() {
  if (password.value !== confirm.value) {
    alert('两次输入的密码不一致')
    return
  }
  // 这里写注册逻辑
  alert(`注册成功（身份：${role.value}，手机号：${phone.value}，请替换为实际逻辑）`)
  router.push('/login')
}
</script>

<style scoped>
.register-root {
  min-height: 100vh;
  background: linear-gradient(120deg, #f8fafc 0%, #e0ffe7 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
}
.register-card {
  display: flex;
  width: 720px;
  min-height: 420px;
  border-radius: 32px;
  box-shadow: 0 8px 32px 0 #6a82fb22, 0 1.5px 6px 0 #fc5c7d11;
  background: rgba(255,255,255,0.97);
  overflow: hidden;
  backdrop-filter: blur(18px) saturate(180%);
  animation: card-pop 0.8s cubic-bezier(.68,-0.55,.27,1.55);
}
@keyframes card-pop {
  0% { transform: scale(0.92) translateY(30px); opacity: 0; }
  100% { transform: scale(1) translateY(0); opacity: 1; }
}
.register-left {
  flex: 1.1;
  background: linear-gradient(135deg, #43e97b 0%, #6a82fb 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
  position: relative;
  z-index: 1;
}
.register-graphic {
  display: flex;
  flex-direction: column;
  align-items: center;
  animation: fade-in 1.2s;
}
.register-title {
  font-size: 2rem;
  font-weight: 700;
  margin-top: 18px;
  letter-spacing: 2px;
  text-shadow: 0 2px 12px #6a82fb33;
}
.register-desc {
  font-size: 1.1rem;
  margin-top: 10px;
  opacity: 0.85;
  letter-spacing: 1px;
}
.register-right {
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
.register-form {
  width: 90%;
  max-width: 320px;
  display: flex;
  flex-direction: column;
  gap: 18px;
  background: transparent;
  padding: 24px 0 18px 0; /* 上下内边距，避免标题和底部文字贴边 */
}
.form-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #1976d2;
  text-align: center;
  margin-bottom: 10px;
  letter-spacing: 1px;
  padding-top: 8px;
}
.form-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
  position: relative;
}
label {
  font-size: 15px;
  color: #5a5a7a;
  font-weight: 500;
}
input, select {
  padding: 12px 14px;
  border: 1.5px solid #e3eaf7;
  border-radius: 14px;
  font-size: 16px;
  background: rgba(255,255,255,0.7);
  transition: border 0.2s, box-shadow 0.2s;
  outline: none;
  box-shadow: 0 1.5px 6px 0 rgba(0,0,0,0.03);
}
input:focus, select:focus {
  border: 1.5px solid #43e97b;
  background: #fff;
  box-shadow: 0 0 0 2px #38f9d733;
}
.register-btn {
  width: 100%;
  padding: 13px 0;
  background: linear-gradient(90deg, #43e97b 0%, #6a82fb 100%);
  color: #fff;
  border: none;
  border-radius: 14px;
  font-size: 18px;
  font-weight: 600;
  cursor: pointer;
  margin-top: 10px;
  box-shadow: 0 2px 8px #6a82fb22;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  transition: background 0.2s, box-shadow 0.2s, transform 0.15s;
}
.register-btn:hover {
  background: linear-gradient(90deg, #6a82fb 0%, #43e97b 100%);
  box-shadow: 0 4px 16px 0 #fc5c7d33;
  transform: translateY(-2px) scale(1.03);
}
.btn-icon {
  font-size: 20px;
}
.to-login {
  margin-top: 18px;
  text-align: right;
  font-size: 15px;
  padding-bottom: 8px; /* 底部留白 */
}
.to-login a {
  color: #11998e;
  text-decoration: underline;
  margin-left: 4px;
  font-weight: 500;
}
::-webkit-input-placeholder { color: #b0b8c9; }
::-moz-placeholder { color: #b0b8c9; }
:-ms-input-placeholder { color: #b0b8c9; }
::placeholder { color: #b0b8c9; }
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
  color: #11998e;
  border: 1.5px solid transparent;
  transition: background 0.2s, border 0.2s, color 0.2s, box-shadow 0.2s;
  box-shadow: 0 1.5px 6px 0 rgba(67,233,123,0.04);
  position: relative;
  user-select: none;
}
.role-option input[type="radio"] {
  display: none;
}
.role-option.active {
  background: linear-gradient(90deg, #11998e 0%, #38ef7d 100%);
  color: #fff;
  border: 1.5px solid #11998e;
  box-shadow: 0 2px 8px 0 #11998e22;
}
.role-option.active .role-label {
  font-weight: 700;
}
.role-option:hover:not(.active) {
  background: #e0ffe7;
  color: #38ef7d;
  border: 1.5px solid #38ef7d44;
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
</style>
