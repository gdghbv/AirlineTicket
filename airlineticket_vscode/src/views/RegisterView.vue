<template>
  <div class="register-root fade-in-page">
    <div class="register-card card-pop">
      <div class="register-left">
        <div class="register-graphic" ref="planeArea">
          <svg id="plane-svg" width="120" height="120" viewBox="0 0 120 120">
            <defs>
              <linearGradient id="lg2" x1="0" y1="0" x2="1" y2="1">
                <stop offset="0%" stop-color="#43e97b"/>
                <stop offset="100%" stop-color="#38f9d7"/>
              </linearGradient>
            </defs>
            <circle cx="60" cy="60" r="55" fill="url(#lg2)" opacity="0.7"/>
            <circle cx="60" cy="60" r="40" fill="#fff" opacity="0.15"/>
            <circle cx="60" cy="60" r="25" fill="#fff" opacity="0.08"/>
            <!-- 飞机SVG -->
            <g id="plane-group">
              <polygon points="60,30 65,60 60,55 55,60" fill="#1976d2"/>
              <rect x="57" y="60" width="6" height="18" rx="2" fill="#1976d2"/>
              <rect x="54" y="78" width="12" height="4" rx="2" fill="#43e97b"/>
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
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" maxlength="40" prefix-icon="i-ep-message" />
              </el-form-item>
              <el-form-item label="机场ID" prop="airportId">
                <el-input v-model="form.airportId" maxlength="10" prefix-icon="i-ep-office-building" />
              </el-form-item>
              <el-form-item label="机场地址" prop="address">
                <el-input v-model="form.address" maxlength="50" prefix-icon="i-ep-location" />
              </el-form-item>
              <el-form-item label="机场名称" prop="airportName">
                <el-input v-model="form.airportName" maxlength="30" prefix-icon="i-ep-takeaway-box" />
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
  address: '',
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
  address: [
    { required: true, message: '请输入机场地址', trigger: 'blur' }
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
    address: '',
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
          email: form.value.email,
          password: form.value.password,
          airportId: form.value.airportId,
          address: form.value.address,
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
.register-root {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(120deg, #f8fafc 0%, #e0ffe7 100%);
  font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
  z-index: 10;
}
.register-card {
  display: flex;
  width: 820px;
  max-width: 95vw;
  min-height: 480px;
  border-radius: 32px;
  box-shadow: 0 8px 32px 0 #43e97b22, 0 1.5px 6px 0 #38f9d711;
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
.animated-slide-in-right { animation: fade-in-right 1.2s; }
.animated-bounce-in { animation: bounce-in 0.7s; }
@keyframes bounce-in {
  0% { transform: scale(0.8); opacity: 0; }
  60% { transform: scale(1.05); opacity: 1; }
  100% { transform: scale(1); }
}
.register-el-card {
  width: 100%;
  box-shadow: none;
  background: transparent;
  border: none;
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
.user-type-group {
  display: flex;
  justify-content: center;
  margin-bottom: 18px;
}
.register-form {
  margin-top: 10px;
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
  box-shadow: 0 4px 16px 0 #43e97b33;
  transform: translateY(-2px) scale(1.03);
}
.fade-in-page {
  animation: fade-in-page 0.8s cubic-bezier(.68,-0.55,.27,1.55);
}
@keyframes fade-in-page {
  0% { opacity: 0; transform: translateY(40px) scale(0.98); }
  100% { opacity: 1; transform: translateY(0) scale(1); }
}
@media (max-width: 900px) {
  .register-card {
    flex-direction: column;
    width: 98vw;
    min-width: 0;
    border-radius: 18px;
  }
  .register-left, .register-right {
    flex: none;
    width: 100%;
    min-width: 0;
    min-height: 180px;
  }
}
</style>
