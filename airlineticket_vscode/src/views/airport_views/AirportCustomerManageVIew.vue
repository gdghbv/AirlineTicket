<template>
  <div class="customer-manage">
    <el-card class="customer-card" shadow="never">
      <template #header>
        <div class="card-header">
          <span>客户管理</span>
          <el-button type="primary" @click="refreshData">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </template>

      <!-- 搜索表单 -->
      <div class="search-section">
        <el-form :model="searchForm" inline>
          <el-form-item label="客户ID">
            <el-input 
              v-model="searchForm.customerId" 
              placeholder="请输入客户ID"
              clearable
              style="width: 180px"
            />
          </el-form-item>
          <el-form-item label="用户名">
            <el-input 
              v-model="searchForm.userName" 
              placeholder="请输入用户名"
              clearable
              style="width: 180px"
            />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input 
              v-model="searchForm.phone" 
              placeholder="请输入手机号"
              clearable
              style="width: 180px"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">
              <el-icon><Search /></el-icon>
              搜索
            </el-button>
            <el-button @click="resetSearch">
              <el-icon><Refresh /></el-icon>
              重置
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 客户列表表格 -->
      <el-table 
        :data="customerList" 
        v-loading="loading"
        stripe
        style="width: 100%"
        @sort-change="handleSortChange"
      >
        <el-table-column prop="customerId" label="客户ID" width="80" sortable />
        <el-table-column prop="userName" label="用户名" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="milsPoints" label="积分" width="100" sortable>
          <template #default="scope">
            <el-tag :type="scope.row.milsPoints > 1000 ? 'success' : 'info'">
              {{ scope.row.milsPoints || 0 }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="会员状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.milsPoints > 0 ? 'success' : 'info'">
              {{ scope.row.milsPoints > 0 ? '会员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="editCustomer(scope.row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="deleteCustomerConfirm(scope.row)"
            >
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="pagination.pageNum"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.totalSize"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 编辑客户对话框 -->
    <el-dialog 
      title="编辑客户信息" 
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
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="editForm.phone" placeholder="请输入手机号" disabled />
        </el-form-item>
        <el-form-item label="积分">
          <el-input-number 
            v-model="editForm.milsPoints" 
            :min="0"
            :max="999999"
            style="width: 100%"
          />
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
import { Edit, Delete, Refresh, Search } from '@element-plus/icons-vue'
import * as airportApi from '@/api/airport'

// 响应式数据
const loading = ref(false)
const customerList = ref([])
const dialogVisible = ref(false)
const editFormRef = ref(null)

// 搜索表单
const searchForm = reactive({
  customerId: '',
  userName: '',
  phone: ''
})

// 编辑表单
const editForm = reactive({
  customerId: null,
  userName: '',
  email: '',
  phone: '',
  milsPoints: 0
})

// 分页信息
const pagination = reactive({
  pageNum: 1,
  pageSize: 10,
  totalSize: 0
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

// 获取客户列表
const getCustomerList = async () => {
  try {
    loading.value = true
    // 根据接口文档，使用正确的参数名
    const params = {
      idKeywords: searchForm.customerId || '',
      nameKeywords: searchForm.userName || '',
      phoneKeywords: searchForm.phone || '',
      emailKeywords: '',
      pageNum: pagination.pageNum.toString(),
      pageSize: pagination.pageSize.toString()
    }
    
    console.log('搜索参数:', params) // 添加调试日志
    
    const data = await airportApi.getAllCustomers(params)
    console.log('API响应:', data) // 添加调试日志
    
    customerList.value = data.pageData || []
    pagination.totalSize = data.totalSize || 0
    
    if (customerList.value.length === 0 && pagination.pageNum > 1) {
      // 如果当前页没有数据且不是第一页，回到第一页
      pagination.pageNum = 1
      getCustomerList()
    }
  } catch (error) {
    console.error('获取客户列表失败:', error)
    ElMessage.error('获取客户列表失败: ' + (error.message || '未知错误'))
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  console.log('执行搜索，参数:', searchForm)
  pagination.pageNum = 1
  getCustomerList()
}

// 重置搜索
const resetSearch = () => {
  Object.assign(searchForm, {
    customerId: '',
    userName: '',
    phone: ''
  })
  pagination.pageNum = 1
  console.log('重置搜索参数')
  getCustomerList()
}

// 编辑客户
const editCustomer = (row) => {
  Object.assign(editForm, {
    customerId: row.customerId,
    userName: row.userName || '',
    email: row.email || '',
    phone: row.phone || '',
    milsPoints: row.milsPoints || 0
  })
  dialogVisible.value = true
}

// 删除客户确认
const deleteCustomerConfirm = (row) => {
  ElMessageBox.confirm(
    `确定要删除客户 "${row.userName || row.phone}" 吗？`,
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    deleteCustomerById(row.customerId)
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 删除客户
const deleteCustomerById = async (id) => {
  try {
    await airportApi.deleteCustomer(id)
    ElMessage.success('删除成功')
    getCustomerList()
  } catch (error) {
    console.error('删除客户失败:', error)
    ElMessage.error('删除失败')
  }
}

// 提交表单
const submitForm = () => {
  editFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 确保提交的数据格式正确
        const updateData = {
          customerId: editForm.customerId,
          userName: editForm.userName,
          email: editForm.email,
          phone: editForm.phone,
          milsPoints: editForm.milsPoints,
          password: '81dc9bdb52d04dc20036dbd8313ed055' // 默认密码哈希
        }
        
        console.log('提交数据:', updateData)
        await airportApi.updateCustomer(updateData)
        ElMessage.success('修改成功')
        dialogVisible.value = false
        getCustomerList()
      } catch (error) {
        console.error('修改失败:', error)
        ElMessage.error('修改失败: ' + (error.message || '未知错误'))
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

// 分页大小改变
const handleSizeChange = (val) => {
  pagination.pageSize = val
  pagination.pageNum = 1
  getCustomerList()
}

// 当前页改变
const handleCurrentChange = (val) => {
  pagination.pageNum = val
  getCustomerList()
}

// 排序改变
const handleSortChange = ({ column, prop, order }) => {
  // 这里可以根据需要实现排序逻辑
  console.log('排序:', { column, prop, order })
}

// 刷新数据
const refreshData = () => {
  console.log('刷新数据')
  getCustomerList()
}

// 组件挂载时获取数据
onMounted(() => {
  console.log('组件挂载，获取客户列表')
  getCustomerList()
})
</script>

<style scoped>
.customer-manage {
  height: 100%;
  padding: 20px;
}

.customer-card {
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

.search-section {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 6px;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.dialog-footer {
  display: flex;
  gap: 10px;
}

:deep(.el-card__body) {
  height: calc(100% - 60px);
  display: flex;
  flex-direction: column;
}

:deep(.el-table) {
  font-size: 14px;
  flex: 1;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
}

:deep(.el-form-item__label) {
  font-weight: 500;
}

:deep(.el-tag) {
  font-weight: 500;
}
</style>