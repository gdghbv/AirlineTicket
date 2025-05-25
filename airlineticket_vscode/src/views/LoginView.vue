<template>
  <div class="login-root fade-in-page">
    <div class="login-card card-pop">
      <div class="login-left">
        <div class="login-graphic">
          <svg id="plane-svg" width="120" height="120" viewBox="0 0 120 120">
            <defs>
              <linearGradient id="lg-login" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#6a82fb" />
                <stop offset="100%" stop-color="#fc5c7d" />
              </linearGradient>
            </defs>
            <circle cx="60" cy="60" r="55" fill="url(#lg-login)" opacity="0.7" />
            <circle cx="60" cy="60" r="40" fill="#fff" opacity="0.15" />
            <circle cx="60" cy="60" r="25" fill="#fff" opacity="0.08" />
            <!-- 飞机SVG -->
            <g id="plane-group">
              <polygon points="60,30 65,60 60,55 55,60" fill="#6a82fb" />
              <rect x="57" y="60" width="6" height="18" rx="2" fill="#6a82fb" />
              <rect x="54" y="78" width="12" height="4" rx="2" fill="#fc5c7d" />
            </g>
          </svg>
          <div class="login-title">欢迎登录</div>
          <div class="login-desc">智能出行，从这里开始</div>
        </div>
      </div>
      <div class="login-right animated-slide-in-right">
        <el-card class="login-el-card">
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
        
        router.push('/airportHome')
      }
    } catch (e) {
      // 错误提示已由拦截器处理
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
/* 登录页面整体全屏居中背景 */
.login-root {
  position: fixed; /* 固定定位，始终覆盖全屏 */
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex; /* 启用flex居中 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
  background: linear-gradient(120deg, #e0e7ff 0%, #f8fafc 100%); /* 渐变背景 */
  font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
  z-index: 10;
}

/* 登录卡片主容器，双栏布局 */
.login-card {
  display: flex; /* 横向排列左右两栏 */
  width: 800px; /* 固定宽度，适中 */
  max-width: 95vw; /* 小屏自适应 */
  min-height: 440px;
  border-radius: 32px; /* 圆角 */
  box-shadow: 0 8px 32px 0 #6a82fb22, 0 1.5px 6px 0 #fc5c7d11; /* 阴影 */
  background: rgba(255, 255, 255, 0.98); /* 半透明白底 */
  overflow: hidden;
  backdrop-filter: blur(18px) saturate(180%); /* 毛玻璃效果 */
  animation: card-pop 0.8s cubic-bezier(.68, -0.55, .27, 1.55); /* 入场动画 */
}

/* 卡片入场动画 */
@keyframes card-pop {
  0% {
    transform: scale(0.92) translateY(30px);
    opacity: 0;
  }

  100% {
    transform: scale(1) translateY(0);
    opacity: 1;
  }
}

/* 左侧插画区，渐变背景+飞机SVG */
.login-left {
  flex: 1.1; /* 占比略小 */
  background: linear-gradient(135deg, #6a82fb 0%, #fc5c7d 100%); /* 蓝紫-粉红渐变 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
  position: relative;
  z-index: 1;
}

/* 插画内容居中+入场动画 */
.login-graphic {
  display: flex;
  flex-direction: column;
  align-items: center;
  animation: fade-in 1.2s;
}

/* 欢迎标题 */
.login-title {
  font-size: 2rem;
  font-weight: 700;
  margin-top: 18px;
  letter-spacing: 2px;
  text-shadow: 0 2px 12px #6a82fb33;
}

/* 副标题描述 */
.login-desc {
  font-size: 1.1rem;
  margin-top: 10px;
  opacity: 0.85;
  letter-spacing: 1px;
}

/* 右侧表单区 */
.login-right {
  flex: 1.2; /* 占比略大 */
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.94); /* 半透明白底 */
  position: relative;
  z-index: 2;
  animation: fade-in-right 1.2s;
}

/* 插画fade-in动画 */
@keyframes fade-in {
  0% {
    opacity: 0;
    transform: translateY(40px);
  }

  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 右侧表单fade-in动画 */
@keyframes fade-in-right {
  0% {
    opacity: 0;
    transform: translateX(40px);
  }

  100% {
    opacity: 1;
    transform: translateX(0);
  }
}

.animated-slide-in-right {
  animation: fade-in-right 1.2s;
}

/* 去除el-card默认背景和阴影 */
.login-el-card {
  width: 100%;
  box-shadow: none;
  background: transparent;
  border: none;
}

/* 表单标题 */
.form-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: #6a82fb;
  text-align: center;
  margin-bottom: 10px;
  letter-spacing: 1px;
  padding-top: 8px;
}

/* 用户类型切换按钮组 */
.user-type-group {
  display: flex;
  justify-content: center;
  margin-bottom: 18px;
}

/* 登录表单整体间距 */
.login-form {
  margin-top: 10px;
}

/* 登录按钮美化 */
.login-btn {
  width: 100%;
  padding: 13px 0;
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%); /* 渐变按钮 */
  color: #fff;
  border: none;
  border-radius: 14px;
  font-size: 18px;
  font-weight: 600;
  cursor: pointer;
  margin-top: 10px;
  box-shadow: 0 2px 8px #fc5c7d22;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  transition: background 0.2s, box-shadow 0.2s, transform 0.15s;
}

/* 按钮悬浮高亮 */
.login-btn:hover {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
  box-shadow: 0 4px 16px 0 #fc5c7d33;
  transform: translateY(-2px) scale(1.03);
}

/* 注册跳转文字 */
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

/* 响应式：小屏下纵向排列 */
@media (max-width: 900px) {
  .login-card {
    flex-direction: column;
    width: 98vw;
    min-width: 0;
    border-radius: 18px;
  }

  .login-left,
  .login-right {
    flex: none;
    width: 100%;
    min-width: 0;
    min-height: 180px;
  }
}
</style>
