<template>
  <div class="login-root fade-in-page">
    <div class="login-card card-pop">
      <div class="login-left">
        <div class="login-graphic">          <svg id="plane-svg" width="120" height="120" viewBox="0 0 120 120">
            <defs>
              <linearGradient id="lg-login" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#667eea" />
                <stop offset="100%" stop-color="#764ba2" />
              </linearGradient>
              <linearGradient id="plane-gradient" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#ffffff" />
                <stop offset="50%" stop-color="#f0f0f0" />
                <stop offset="100%" stop-color="#e0e0e0" />
              </linearGradient>
              <filter id="glow">
                <feGaussianBlur stdDeviation="3" result="coloredBlur"/>
                <feMerge> 
                  <feMergeNode in="coloredBlur"/>
                  <feMergeNode in="SourceGraphic"/>
                </feMerge>
              </filter>
            </defs>
              <!-- 背景圆圈 -->
            <circle cx="60" cy="60" r="55" fill="url(#lg-login)" opacity="0.7" style="--circle-opacity: 0.7" />
            <circle cx="60" cy="60" r="40" fill="url(#lg-login)" opacity="0.25" style="--circle-opacity: 0.25" />
            <circle cx="60" cy="60" r="25" fill="url(#lg-login)" opacity="0.15" style="--circle-opacity: 0.15" />
            
            <!-- 飞机图标 -->
            <g id="plane-group" filter="url(#glow)">
              <!-- 机身 -->
              <ellipse cx="60" cy="60" rx="3" ry="20" fill="url(#plane-gradient)" />
              <!-- 机翼 -->
              <ellipse cx="60" cy="55" rx="18" ry="4" fill="url(#plane-gradient)" />
              <!-- 尾翼 -->
              <ellipse cx="60" cy="75" rx="8" ry="3" fill="url(#plane-gradient)" />
              <!-- 机头 -->
              <circle cx="60" cy="42" r="3" fill="#667eea" />
              <!-- 窗户 -->
              <circle cx="60" cy="50" r="1.5" fill="#667eea" opacity="0.6" />
              <circle cx="60" cy="55" r="1.5" fill="#667eea" opacity="0.6" />
              <circle cx="60" cy="60" r="1.5" fill="#667eea" opacity="0.6" />
            </g>
          </svg>
          <div class="login-title">欢迎登录</div>
          <div class="login-desc">智能出行，从这里开始</div>
        </div>
      </div>
      <div class="login-right animated-slide-in-right">
        <el-card class="login-el-card">
          <div class="login-el-card-header">
            <span class="go-home-link" @click="goHome">
              <el-icon style="vertical-align: middle; font-size: 18px; color: #3a7afe; margin-right: 3px;"><i-ep-house /></el-icon>
              <span>去首页</span>
            </span>
          </div>
          <div class="form-title">
            <el-icon style="vertical-align: middle; font-size: 22px; color: #6a82fb;"><i-ep-user /></el-icon>
            用户登录
          </div>
          <el-radio-group v-model="userType" class="user-type-group">
            <el-radio-button label="customer">顾客用户</el-radio-button>
            <el-radio-button label="airport">机场用户</el-radio-button>
          </el-radio-group>
          <el-form :model="form" :rules="rules" ref="loginFormRef" label-width="80px" class="login-form">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" maxlength="20" prefix-icon="i-ep-phone" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" type="password" maxlength="20" prefix-icon="i-ep-lock" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onLogin" :loading="loading" class="login-btn">登录</el-button>
            </el-form-item>
            <el-form-item>
              <span>还没有账号？<router-link to="/register">去注册</router-link></span>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getAirportLogin, getCustomerLogin } from '@/api/index'
import { setToken } from '@/utils/token-utils'
import { ElMessage } from 'element-plus'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const userType = ref('customer')
const loading = ref(false)
const form = ref({
  phone: '',
  password: ''
})
const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 4, message: '密码至少4位', trigger: 'blur' }
  ]
}
const loginFormRef = ref()

const onLogin = () => {
  loginFormRef.value.validate(async (valid) => {
    if (!valid) return
    loading.value = true
    try {
      let res
      if (userType.value === 'customer') {
        res = await getCustomerLogin({ phone: form.value.phone, password: form.value.password })
        setToken(res.token)
        console.log(res.token)
        ElMessage.success('顾客登录成功')
        router.push('/customerHome')
      } else {
        res = await getAirportLogin({ phone: form.value.phone, password: form.value.password })
        setToken(res.token)
        ElMessage.success('机场用户登录成功')
        
        router.push('/airport')
      }
    } catch (e) {
      // 错误提示已由拦截器处理
    } finally {
      loading.value = false
    }
  })
}

const goHome = () => {
  router.push('/customerHome')
}
</script>

<style scoped>
/* 使用theme.css中定义的样式 */

/* 自定义登录页面特定样式 */
.login-left {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
}

.login-graphic svg circle:first-of-type {
  fill: url(#lg-login);
  animation: pulse 4s ease-in-out infinite;
}

.login-graphic svg circle:nth-of-type(2) {
  animation: pulse 4s ease-in-out infinite 0.5s;
}

.login-graphic svg circle:nth-of-type(3) {
  animation: pulse 4s ease-in-out infinite 1s;
}

.form-title .el-icon {
  animation: float 3s ease-in-out infinite;
  color: #667eea !important;
}

/* 输入框获得焦点时的特殊效果 */
.login-form .el-form-item:focus-within .el-form-item__label {
  color: #667eea;
  transform: translateY(-2px);
  transition: all 0.3s ease;
}

/* 登录按钮加载状态动画 */
.login-btn.is-loading {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  animation: pulse 1.5s ease-in-out infinite;
}

/* 页面切换动画 */
@keyframes pageTransition {
  from {
    opacity: 0;
    transform: scale(0.95) translateY(20px);
  }
  to {
    opacity: 1;
    transform: scale(1) translateY(0);
  }
}

.login-root {
  animation: pageTransition 0.6s ease-out;
}

.login-el-card-header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 32px;
  margin-bottom: 2px;
  padding-right: 2px;
}
.go-home-link {
  color: #3a7afe;
  font-size: 16px;
  cursor: pointer;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 2px;
  background: linear-gradient(90deg, #e6fff6 0%, #d2ffe6 100%);
  border-radius: 12px;
  padding: 3px 16px 3px 10px;
  box-shadow: 0 1.5px 6px 0 #6a9bff11;
  transition: background 0.18s, color 0.18s;
}
.go-home-link:hover {
  color: #fff;
  background: linear-gradient(90deg, #6a9bff 0%, #377dff 100%);
  text-decoration: none;
}
</style>
