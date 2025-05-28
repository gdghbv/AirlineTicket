<template>
  <div class="airplane-management">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>飞机管理</h2>
      <el-button type="primary" @click="showAddDialog">
        <el-icon><Plus /></el-icon>
        添加飞机
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <el-card class="search-card" shadow="never">
      <el-form :model="searchForm" inline>
        <el-form-item label="飞机名称">
          <el-input 
            v-model="searchForm.nameKeyword" 
            placeholder="请输入飞机名称"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="飞机状态">
          <el-select 
            v-model="searchForm.statusKeyword" 
            placeholder="请选择状态"
            clearable
            style="width: 200px"
          >
            <el-option label="航行中" value="航行中" />
            <el-option label="维护中" value="维护中" />
            <el-option label="已着陆" value="已着陆" />
          </el-select>
        </el-form-item>
        <el-form-item label="所在位置">
          <el-input 
            v-model="searchForm.locationKeyword" 
            placeholder="请输入位置"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchAirplanes">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="resetSearch">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 飞机列表 -->
    <el-card class="table-card" shadow="never">
      <el-table 
        :data="airplaneList" 
        v-loading="loading"
        style="width: 100%"
        stripe
      >
        <el-table-column prop="airplaneId" label="飞机ID" width="80" />
        <el-table-column prop="airplaneName" label="飞机名称" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag 
              :type="getStatusTagType(scope.row.status)"
              size="small"
            >
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="所在位置" width="120" />
        <el-table-column prop="belongedAirportName" label="所属机场" width="150" />
        <el-table-column label="座位信息" width="200">
          <template #default="scope">
            <div class="seat-info">
              <el-tag size="small" type="success">头等舱: {{ scope.row.firstSeat }}</el-tag>
              <el-tag size="small" type="warning">商务舱: {{ scope.row.secondSeat }}</el-tag>
              <el-tag size="small" type="info">经济舱: {{ scope.row.thirdSeat }}</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="总座位数" width="100">
          <template #default="scope">
            {{ (scope.row.firstSeat || 0) + (scope.row.secondSeat || 0) + (scope.row.thirdSeat || 0) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="editAirplane(scope.row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="deleteAirplaneConfirm(scope.row)"
            >
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div >
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
    </el-card>    <!-- 添加/编辑飞机对话框 -->
    <el-dialog 
      :title="dialogTitle" 
      v-model="dialogVisible" 
      width="800px"
      :before-close="handleClose"
    >      <el-form 
        :model="airplaneForm" 
        :rules="rules" 
        ref="airplaneFormRef"
        label-width="120px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="飞机名称" prop="airplaneName">
              <el-input v-model="airplaneForm.airplaneName" placeholder="请输入飞机名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="飞机状态" prop="status">
              <el-select v-model="airplaneForm.status" placeholder="请选择状态" style="width: 100%">
                <el-option label="航行中" value="航行中" />
                <el-option label="维护中" value="维护中" />
                <el-option label="已着陆" value="已着陆" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="所在位置" prop="location">
              <el-input v-model="airplaneForm.location" placeholder="请输入飞机位置" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="头等舱座位" prop="firstSeat">
              <el-input-number 
                v-model="airplaneForm.firstSeat" 
                :min="0"
                :max="999"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商务舱座位" prop="secondSeat">
              <el-input-number 
                v-model="airplaneForm.secondSeat" 
                :min="0"
                :max="999"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经济舱座位" prop="thirdSeat">
              <el-input-number 
                v-model="airplaneForm.thirdSeat" 
                :min="0"
                :max="999"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleClose">取消</el-button>
          <el-button type="primary" @click="submitForm">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search, Refresh, Edit, Delete } from '@element-plus/icons-vue'
import * as airportApi from '@/api/airport'

// 响应式数据
const loading = ref(false)
const airplaneList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('添加飞机')
const isEditing = ref(false)
const airplaneFormRef = ref(null)

// 搜索表单
const searchForm = reactive({
  nameKeyword: '',
  statusKeyword: '',
  locationKeyword: '',
  pageNum: 1,
  pageSize: 10
})

// 分页信息
const pagination = reactive({
  pageNum: 1,
  pageSize: 10,
  totalSize: 0,
  totalPage: 0
})

// 飞机表单
const airplaneForm = reactive({
  airplaneId: null,
  airplaneName: '',
  status: '',
  location: '',
  firstSeat: 0,
  secondSeat: 0,
  thirdSeat: 0
})

// 表单验证规则
const rules = {
  airplaneName: [
    { required: true, message: '请输入飞机名称', trigger: 'blur' },
    { min: 2, max: 50, message: '飞机名称长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择飞机状态', trigger: 'change' }
  ],
  location: [
    { required: true, message: '请输入飞机位置', trigger: 'blur' },
    { min: 2, max: 50, message: '位置长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  firstSeat: [
    { required: true, message: '请输入头等舱座位数', trigger: 'blur' },
    { type: 'number', min: 0, message: '座位数不能小于0', trigger: 'blur' }
  ],
  secondSeat: [
    { required: true, message: '请输入商务舱座位数', trigger: 'blur' },
    { type: 'number', min: 0, message: '座位数不能小于0', trigger: 'blur' }
  ],
  thirdSeat: [
    { required: true, message: '请输入经济舱座位数', trigger: 'blur' },
    { type: 'number', min: 0, message: '座位数不能小于0', trigger: 'blur' }
  ]
}

// 获取状态标签类型
const getStatusTagType = (status) => {
  const statusMap = {
    '航行中': 'success',
    '维护中': 'warning',
    '已着陆': 'info'
  }
  return statusMap[status] || 'info'
}

// 获取飞机列表
const getAirplaneList = async () => {
  try {
    loading.value = true
    const params = {
      nameKeyword: searchForm.nameKeyword,
      statusKeyword: searchForm.statusKeyword,
      locationKeyword: searchForm.locationKeyword,
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize
    }
    
    const data = await airportApi.getAllAirplanes(params)
    airplaneList.value = data.pageData
    pagination.totalSize = data.totalSize
    pagination.totalPage = data.totalPage
    console.log('获取飞机列表成功:', airplaneList.value)
  } catch (error) {
    console.error('获取飞机列表失败:', error)
    ElMessage.error('获取飞机列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索飞机
const searchAirplanes = () => {
  pagination.pageNum = 1
  getAirplaneList()
}

// 重置搜索
const resetSearch = () => {
  searchForm.nameKeyword = ''
  searchForm.statusKeyword = ''
  searchForm.locationKeyword = ''
  pagination.pageNum = 1
  getAirplaneList()
}

// 分页处理
const handleSizeChange = (val) => {
  pagination.pageSize = val
  pagination.pageNum = 1
  getAirplaneList()
}

const handleCurrentChange = (val) => {
  pagination.pageNum = val
  getAirplaneList()
}

// 显示添加对话框
const showAddDialog = () => {
  dialogTitle.value = '添加飞机'
  isEditing.value = false
  resetForm()
  dialogVisible.value = true
}

// 编辑飞机
const editAirplane = (row) => {
  dialogTitle.value = '编辑飞机'
  isEditing.value = true
  Object.assign(airplaneForm, row)
  dialogVisible.value = true
}

// 删除飞机确认
const deleteAirplaneConfirm = (row) => {
  ElMessageBox.confirm(
    `确定要删除飞机 "${row.airplaneName}" 吗？`,
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    deleteAirplaneById(row.airplaneId)
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 删除飞机
const deleteAirplaneById = async (id) => {
  try {
    await airportApi.deleteAirplane(id)
    ElMessage.success('删除成功')
    getAirplaneList()
  } catch (error) {
    console.error('删除飞机失败:', error)
    ElMessage.error('删除失败')
  }
}

// 提交表单
const submitForm = () => {
  airplaneFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await (isEditing.value 
          ? airportApi.updateAirplane(airplaneForm)
          : airportApi.addAirplane(airplaneForm))
        
        ElMessage.success(isEditing.value ? '修改成功' : '添加成功')
        dialogVisible.value = false
        getAirplaneList()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error(isEditing.value ? '修改失败' : '添加失败')
      }
    }
  })
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  resetForm()
}

// 重置表单
const resetForm = () => {
  Object.assign(airplaneForm, {
    airplaneId: null,
    airplaneName: '',
    status: '',
    location: '',
    firstSeat: 0,
    secondSeat: 0,
    thirdSeat: 0
  })
  if (airplaneFormRef.value) {
    airplaneFormRef.value.clearValidate()
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getAirplaneList()
})
</script>

<style scoped>
.airplane-management {
  padding: 24px;
  background: #f7fafc;
  min-height: 100vh;
  box-sizing: border-box;
}

.page-header {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 20px;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #3a7afe;
  font-size: 2rem;
  font-weight: 600;
}

/* 让添加按钮更显眼 */
.page-header .el-button {
  margin-left: 16px;
  font-size: 16px;
  padding: 8px 20px;
  background: #3a7afe;
  color: #fff;
  border-radius: 6px;
  border: none;
  box-shadow: 0 2px 8px 0 rgba(58,122,254,0.08);
  transition: background 0.2s;
}
.page-header .el-button:hover {
  background: #2466d1;
}

.search-card {
  margin-bottom: 20px;
}

.table-card {
  margin-bottom: 20px;
}

.seat-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
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

:deep(.el-table) {
  font-size: 14px;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
}

:deep(.el-card__body) {
  padding: 20px;
}

:deep(.el-form-item__label) {
  font-weight: 500;
}

:deep(.el-dialog__body) {
  padding: 20px 24px;
}

:deep(.el-dialog .el-form-item) {
  margin-bottom: 20px;
}

:deep(.el-input-number) {
  width: 100% !important;
}
</style>