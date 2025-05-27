<template>
  <div class="airport-user-all">
    <el-card class="user-list-card" shadow="never">
      <template #header>
        <div class="card-header">
          <span>所有机场用户</span>
          <el-button type="primary" @click="refreshData">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </template>

      <!-- 用户列表 -->
      <el-table 
        :data="userList" 
        v-loading="loading"
        style="width: 100%"
        stripe
        height="100%"
      >
        <el-table-column prop="userId" label="用户ID" width="80" />
        <el-table-column prop="airportId" label="机场ID" width="80" />
        <el-table-column prop="airportName" label="机场名称" width="200" />
        <el-table-column prop="userName" label="用户名" width="120">
          <template #default="scope">
            {{ scope.row.userName || '未设置' }}
          </template>
        </el-table-column>
        <el-table-column prop="airportPhone" label="机场电话" width="200" />
        <el-table-column prop="phone" label="电话" width="120" />
        <el-table-column prop="address" label="地址" min-width="200" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="editUser(scope.row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="deleteUserConfirm(scope.row)"
              :disabled="scope.row.airportUserId === currentUserId"
            >
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 编辑用户对话框 -->
    <el-dialog 
      title="编辑用户信息" 
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
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email" placeholder="请输入邮箱" />
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
import { ElMessage, ElMessageBox } from 'element-plus'
import { Edit, Delete, Refresh } from '@element-plus/icons-vue'
import * as airportApi from '@/api/airport'

// 响应式数据
const loading = ref(false)
const userList = ref([])
const dialogVisible = ref(false)
const editFormRef = ref(null)
const currentUserId = ref(null)

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

// 获取所有用户信息
const getAllUsers = async () => {
  try {
    loading.value = true
    const data = await airportApi.getAllAirportUsers()
    userList.value = data
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
  } finally {
    loading.value = false
  }
}

// 获取当前登录用户ID
const getCurrentUserId = async () => {
  try {
    const data = await airportApi.getAirportInfo()
    currentUserId.value = data.airportUserId
  } catch (error) {
    console.error('获取当前用户信息失败:', error)
  }
}

// 编辑用户
const editUser = (row) => {
  editForm.airportUserId = row.airportUserId
  editForm.userName = row.userName || ''
  editForm.email = row.email || ''
  dialogVisible.value = true
}

// 删除用户确认
const deleteUserConfirm = (row) => {
  if (row.airportUserId === currentUserId.value) {
    ElMessage.warning('不能删除自己的账号')
    return
  }

  ElMessageBox.confirm(
    `确定要删除用户 "${row.userName || row.email}" 吗？`,
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    deleteUserById(row.airportUserId)
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 删除用户
const deleteUserById = async (id) => {
  try {
    await airportApi.deleteAirportUser(id)
    ElMessage.success('删除成功')
    getAllUsers()
  } catch (error) {
    console.error('删除用户失败:', error)
    ElMessage.error('删除失败')
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
        getAllUsers()
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

// 刷新数据
const refreshData = () => {
  getAllUsers()
}

// 组件挂载时获取数据
onMounted(() => {
  getCurrentUserId()
  getAllUsers()
})
</script>

<style scoped>
.airport-user-all {
  height: 100%;
}

.user-list-card {
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

.dialog-footer {
  display: flex;
  gap: 10px;
}

:deep(.el-card__body) {
  height: calc(100% - 60px);
  padding: 0;
}

:deep(.el-table) {
  font-size: 14px;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
}
</style>