<template>
  <div class="register-root fade-in-page">
    <div class="register-card card-pop">
      <div class="register-left">
        <div class="register-graphic" ref="planeArea">          <svg id="plane-svg" width="120" height="120" viewBox="0 0 120 120">
            <defs>              <linearGradient id="lg2" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#4facfe"/>
                <stop offset="100%" stop-color="#00c6fb"/>
              </linearGradient>
              <linearGradient id="plane-gradient-register" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#ffffff" />
                <stop offset="50%" stop-color="#f0f0f0" />
                <stop offset="100%" stop-color="#e0e0e0" />
              </linearGradient>
              <filter id="glow-register">
                <feGaussianBlur stdDeviation="3" result="coloredBlur"/>
                <feMerge> 
                  <feMergeNode in="coloredBlur"/>
                  <feMergeNode in="SourceGraphic"/>
                </feMerge>
              </filter>
            </defs>
              <!-- 背景圆圈 -->
            <circle cx="60" cy="60" r="55" fill="url(#lg2)" opacity="0.7" style="--circle-opacity: 0.7"/>
            <circle cx="60" cy="60" r="40" fill="url(#lg2)" opacity="0.25" style="--circle-opacity: 0.25"/>
            <circle cx="60" cy="60" r="25" fill="url(#lg2)" opacity="0.15" style="--circle-opacity: 0.15"/>
            
            <!-- 飞机图标 -->
            <g id="plane-group" filter="url(#glow-register)">
              <!-- 机身 -->
              <ellipse cx="60" cy="60" rx="3" ry="20" fill="url(#plane-gradient-register)" />
              <!-- 机翼 -->
              <ellipse cx="60" cy="55" rx="18" ry="4" fill="url(#plane-gradient-register)" />
              <!-- 尾翼 -->
              <ellipse cx="60" cy="75" rx="8" ry="3" fill="url(#plane-gradient-register)" />
              <!-- 机头 -->
              <circle cx="60" cy="42" r="3" fill="#43e97b" />
              <!-- 窗户 -->
              <circle cx="60" cy="50" r="1.5" fill="#43e97b" opacity="0.6" />
              <circle cx="60" cy="55" r="1.5" fill="#43e97b" opacity="0.6" />
              <circle cx="60" cy="60" r="1.5" fill="#43e97b" opacity="0.6" />
            </g>
          </svg>
          <div class="register-title">欢迎注册</div>
          <div class="register-desc">开启智能出行新体验</div>
        </div>
      </div>
      <div class="register-right animated-slide-in-right">
        <el-card class="register-el-card">
          <div class="form-title">
            <el-icon style="vertical-align: middle; font-size: 22px; color: #43e97b;"><i-ep-user /></el-icon>
            用户注册
          </div>
          <el-radio-group v-model="userType" class="user-type-group">
            <el-radio-button label="customer">顾客用户</el-radio-button>
            <el-radio-button label="airport">机场用户</el-radio-button>
          </el-radio-group>
          <el-form :model="form" :rules="rules" ref="registerFormRef" label-width="100px" class="register-form">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" maxlength="20" prefix-icon="i-ep-phone" />
            </el-form-item>
            <el-form-item v-if="userType==='customer'" label="用户名" prop="userName">
              <el-input v-model="form.userName" maxlength="20" prefix-icon="i-ep-user" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" type="password" maxlength="20" prefix-icon="i-ep-lock" />
            </el-form-item>
            <template v-if="userType==='airport'">
          
              <el-form-item label="机场ID" prop="airportId">
                <el-input v-model="form.airportId" maxlength="10" prefix-icon="i-ep-office-building" />
              </el-form-item>
              <el-form-item label="用户名" prop="userName">
                <el-input v-model="form.userName" maxlength="50" prefix-icon="i-ep-location" />
              </el-form-item>
          
            </template>
            <el-form-item>
              <el-button type="primary" @click="onRegister" :loading="loading" class="register-btn">注册</el-button>
            </el-form-item>
            <el-form-item>
              <span>已有账号？<router-link to="/login">去登录</router-link></span>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { CustomerRegister, AirportRegister } from '@/api/index'
import { User as IepUser, Phone as IepPhone, Lock as IepLock, Message as IepMessage, OfficeBuilding as IepOfficeBuilding, Location as IepLocation, TakeawayBox as IepTakeawayBox } from '@element-plus/icons-vue'

const router = useRouter()
const userType = ref('customer')
const loading = ref(false)
const form = ref({
  phone: '',
  userName: '',
  password: '',
  email: '',
  airportId: '',
  
  airportName: ''
})
const rules = ref({
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 4, message: '密码至少4位', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  airportId: [
    { required: true, message: '请输入机场ID', trigger: 'blur' }
  ],
 
  airportName: [
    { required: true, message: '请输入机场名称', trigger: 'blur' }
  ]
})
const registerFormRef = ref()
const planeArea = ref(null)

watch(userType, (val) => {
  // 切换用户类型时清空表单
  form.value = {
    phone: '',
    userName: '',
    password: '',
    email: '',
    airportId: '',
   
    airportName: ''
  }
})

const onRegister = () => {
  registerFormRef.value.validate(async (valid) => {
    if (!valid) return
    loading.value = true
    try {
      if (userType.value === 'customer') {
        await CustomerRegister({
          phone: form.value.phone,
          userName: form.value.userName,
          password: form.value.password
        })
        ElMessage.success('顾客注册成功，请登录')
        router.push('/login')
      } else {
        await AirportRegister({
          phone: form.value.phone,
          password: form.value.password,
          airportId: form.value.airportId,
          userName: form.value.userName,
          airportName: form.value.airportName
        })
        ElMessage.success('机场用户注册成功，请登录')
        router.push('/login')
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
/* 使用theme.css中定义的样式 */

/* 自定义注册页面特定样式 */
.register-left {
  background: linear-gradient(135deg, rgba(67, 233, 123, 0.1) 0%, rgba(56, 249, 215, 0.1) 100%);
}

.register-graphic svg circle:first-of-type {
  fill: url(#lg2);
  animation: pulse 4s ease-in-out infinite;
}

.register-graphic svg circle:nth-of-type(2) {
  animation: pulse 4s ease-in-out infinite 0.5s;
}

.register-graphic svg circle:nth-of-type(3) {
  animation: pulse 4s ease-in-out infinite 1s;
}

.form-title .el-icon {
  animation: float 3s ease-in-out infinite;
  color: #43e97b !important;
}

/* 注册表单特殊样式 */
.register-form .el-form-item {
  transition: all 0.3s ease;
}

.register-form .el-form-item:hover {
  transform: translateX(5px);
}

/* 输入框获得焦点时的特殊效果 */
.register-form .el-form-item:focus-within .el-form-item__label {
  color: #43e97b;
  transform: translateY(-2px);
  transition: all 0.3s ease;
}

/* 注册按钮特殊样式 */
.register-btn {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  box-shadow: 0 8px 25px rgba(67, 233, 123, 0.3);
}

.register-btn:hover {
  box-shadow: 0 12px 35px rgba(67, 233, 123, 0.4);
}

.register-btn.is-loading {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  animation: pulse 1.5s ease-in-out infinite;
}

/* 用户类型切换动画 */
.user-type-group .el-radio-button__input:checked + .el-radio-button__inner {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  box-shadow: 0 4px 15px rgba(67, 233, 123, 0.4);
}

/* 表单项动态显示动画 */
.register-form .el-form-item {
  animation: slideInUp 0.5s ease-out;
}

@keyframes slideInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
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

.register-root {
  animation: pageTransition 0.6s ease-out;
}

/* 机场表单项延迟动画 */
.register-form .el-form-item:nth-child(4) { animation-delay: 0.1s; }
.register-form .el-form-item:nth-child(5) { animation-delay: 0.2s; }
.register-form .el-form-item:nth-child(6) { animation-delay: 0.3s; }
.register-form .el-form-item:nth-child(7) { animation-delay: 0.4s; }
.register-form .el-form-item:nth-child(8) { animation-delay: 0.5s; }
</style>
