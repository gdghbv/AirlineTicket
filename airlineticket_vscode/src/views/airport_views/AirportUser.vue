<template>
  <div class="airport-user">
    <el-card class="user-info-card" shadow="never">
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
        </div>
      </template>

      <div v-loading="loading" class="user-info-content">
        <div v-if="userInfo" class="info-section">
          <el-descriptions :column="2" size="large" border>
            <el-descriptions-item label="用户ID">
              {{ userInfo.userId }}
            </el-descriptions-item>
            <el-descriptions-item label="机场ID">
              {{ userInfo.airportId }}
            </el-descriptions-item>
            <el-descriptions-item label="机场名称">
              {{ userInfo.airportName }}
            </el-descriptions-item>
            <el-descriptions-item label="用户名">
              {{ userInfo.userName || '未设置' }}
            </el-descriptions-item>
            <el-descriptions-item label="机场电话">
              {{ userInfo.airportPhone }}
            </el-descriptions-item>
            <el-descriptions-item label="电话">
              {{ userInfo.phone }}
            </el-descriptions-item>
            <el-descriptions-item label="地址" :span="2">
              {{ userInfo.address }}
            </el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="action-section">
          <el-button type="primary" @click="showEditDialog">
            <el-icon><Edit /></el-icon>
            修改个人信息
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 修改个人信息对话框 -->
    <el-dialog 
      title="修改个人信息" 
      v-model="dialogVisible" 
      width="500px"
      :before-close="handleClose"
    >
      <el-form 
        :model="editForm" 
        :rules="rules" 
        ref="editFormRef"
        label-width="80px"
      >
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="editForm.userName" placeholder="请输入用户名" />
        </el-form-item>
        
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleClose">取消</el-button>
          <el-button type="primary" @click="submitForm">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Edit } from '@element-plus/icons-vue'
import * as airportApi from '@/api/airport'

// 响应式数据
const loading = ref(false)
const userInfo = ref(null)
const dialogVisible = ref(false)
const editFormRef = ref(null)

// 编辑表单
const editForm = reactive({
  airportUserId: null,
  userName: '',
  email: ''
})

// 表单验证规则
const rules = {
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { 
      type: 'email', 
      message: '请输入正确的邮箱格式', 
      trigger: ['blur', 'change'] 
    }
  ]
}

// 获取用户信息
const getUserInfo = async () => {
  try {
    loading.value = true
    const res = await airportApi.getAirportInfo()
    // 兼容后端直接返回数组的情况
    if (Array.isArray(res) && res.length > 0) {
      userInfo.value = res[0]
    } else if (res && Array.isArray(res.data) && res.data.length > 0) {
      userInfo.value = res.data[0]
    } else {
      userInfo.value = null
    }
    console.log('获取用户信息成功:', userInfo.value)
  } catch (error) {
    console.error('获取用户信息失败:', error)
    ElMessage.error('获取用户信息失败')
  } finally {
    loading.value = false
  }
}

// 显示编辑对话框
const showEditDialog = () => {
  if (userInfo.value) {
    editForm.airportUserId = userInfo.value.airportUserId
    editForm.userName = userInfo.value.userName || ''
    editForm.email = userInfo.value.email || ''
    dialogVisible.value = true
  }
}

// 提交表单
const submitForm = () => {
  editFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await airportApi.updateAdmin(editForm)
        ElMessage.success('修改成功')
        dialogVisible.value = false
        // 重新获取用户信息
        getUserInfo()
      } catch (error) {
        console.error('修改失败:', error)
        ElMessage.error('修改失败')
      }
    }
  })
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  if (editFormRef.value) {
    editFormRef.value.clearValidate()
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getUserInfo()
})
</script>

<style scoped>
.airport-user {
  height: 100%;
}

.user-info-card {
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header span {
  font-size: 16px;
  font-weight: 500;
}

.user-info-content {
  height: calc(100% - 60px);
  display: flex;
  flex-direction: column;
}

.info-section {
  flex: 1;
  margin-bottom: 30px;
}

.action-section {
  display: flex;
  justify-content: center;
  padding: 20px 0;
  border-top: 1px solid #f0f0f0;
}

.dialog-footer {
  display: flex;
  gap: 10px;
}

:deep(.el-descriptions) {
  margin-top: 20px;
}

:deep(.el-descriptions__label) {
  font-weight: 500;
  background-color: #fafafa;
}

:deep(.el-card__body) {
  height: calc(100% - 60px);
}
</style>