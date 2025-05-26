<template>
  <div class="airline-management">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>航班管理</h2>
      <el-button type="primary" @click="showAddDialog">
        <el-icon><Plus /></el-icon>
        添加航班
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <el-card class="search-card" shadow="never">
      <el-form :model="searchForm" inline>
        <el-form-item label="起飞地点">
          <el-input 
            v-model="searchForm.departureKeyword" 
            placeholder="请输入起飞地点"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="目的地">
          <el-input 
            v-model="searchForm.arrivalKeyword" 
            placeholder="请输入目的地"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="出行日期">
          <el-date-picker
            v-model="searchForm.dateKeyword"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchAirlines">
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

    <!-- 航班列表 -->
    <el-card class="table-card" shadow="never">
      <el-table 
        :data="airlineList" 
        v-loading="loading"
        style="width: 100%"
        stripe
      >
        <el-table-column prop="airlineId" label="航班ID" width="80" />
        <el-table-column prop="departure" label="起飞城市" width="100" />
        <el-table-column prop="arrival" label="目的城市" width="100" />
        <el-table-column prop="departureAirportName" label="起飞机场" width="150" />
        <el-table-column prop="arrivalAirportName" label="到达机场" width="150" />
        <el-table-column prop="date" label="航班日期" width="120" />
        <el-table-column prop="departureTime" label="起飞时间" width="100" />
        <el-table-column prop="arrivalTime" label="到达时间" width="100" />
        <el-table-column prop="duration" label="飞行时长" width="100" />
        <el-table-column prop="boardingGate" label="登机口" width="100" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="scope">
            ¥{{ scope.row.price }}
          </template>
        </el-table-column>
        <el-table-column label="座位信息" width="150">
          <template #default="scope">
            <div class="seat-info">
              <el-tag size="small" type="success">头等舱: {{ scope.row.firstSeat }}</el-tag>
              <el-tag size="small" type="warning">商务舱: {{ scope.row.secondSeat }}</el-tag>
              <el-tag size="small" type="info">经济舱: {{ scope.row.thirdSeat }}</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="editAirline(scope.row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="deleteAirlineConfirm(scope.row)"
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

    <!-- 添加/编辑航班对话框 -->
    <el-dialog 
      :title="dialogTitle" 
      v-model="dialogVisible" 
      width="800px"
      :before-close="handleClose"
    >
      <el-form 
        :model="airlineForm" 
        :rules="rules" 
        ref="airlineFormRef"
        label-width="120px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="飞机ID" prop="airplaneId">
              <el-input-number 
                v-model="airlineForm.airplaneId" 
                :min="1"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="起飞机场ID" prop="departureAirportId">
              <el-input-number 
                v-model="airlineForm.departureAirportId" 
                :min="1"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="到达机场ID" prop="arrivalAirportId">
              <el-input-number 
                v-model="airlineForm.arrivalAirportId" 
                :min="1"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登机口" prop="boardingGate">
              <el-input v-model="airlineForm.boardingGate" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="起飞城市" prop="departure">
              <el-input v-model="airlineForm.departure" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到达城市" prop="arrival">
              <el-input v-model="airlineForm.arrival" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="航班日期" prop="date">
              <el-date-picker
                v-model="airlineForm.date"
                type="date"
                placeholder="选择日期"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价格" prop="price">
              <el-input-number 
                v-model="airlineForm.price" 
                :min="0"
                :precision="2"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="起飞时间" prop="departureTime">
              <el-time-picker
                v-model="airlineForm.departureTime"
                format="HH:mm:ss"
                value-format="HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到达时间" prop="arrivalTime">
              <el-time-picker
                v-model="airlineForm.arrivalTime"
                format="HH:mm:ss"
                value-format="HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="飞行时长" prop="duration">
              <el-input v-model="airlineForm.duration" placeholder="例如: 2h30m" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="头等舱座位" prop="firstSeat">
              <el-input-number 
                v-model="airlineForm.firstSeat" 
                :min="0"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商务舱座位" prop="secondSeat">
              <el-input-number 
                v-model="airlineForm.secondSeat" 
                :min="0"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经济舱座位" prop="thirdSeat">
              <el-input-number 
                v-model="airlineForm.thirdSeat" 
                :min="0"
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
const airlineList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('添加航班')
const isEditing = ref(false)
const airlineFormRef = ref(null)

// 搜索表单
const searchForm = reactive({
  departureKeyword: '',
  arrivalKeyword: '',
  dateKeyword: '',
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

// 航班表单
const airlineForm = reactive({
  airlineId: null,
  airplaneId: null,
  departureAirportId: null,
  arrivalAirportId: null,
  boardingGate: '',
  departure: '',
  arrival: '',
  date: '',
  price: null,
  departureTime: '',
  arrivalTime: '',
  duration: '',
  firstSeat: null,
  secondSeat: null,
  thirdSeat: null
})

// 表单验证规则
const rules = {
  airplaneId: [
    { required: true, message: '请输入飞机ID', trigger: 'blur' }
  ],
  departureAirportId: [
    { required: true, message: '请输入起飞机场ID', trigger: 'blur' }
  ],
  arrivalAirportId: [
    { required: true, message: '请输入到达机场ID', trigger: 'blur' }
  ],
  boardingGate: [
    { required: true, message: '请输入登机口', trigger: 'blur' }
  ],
  departure: [
    { required: true, message: '请输入起飞城市', trigger: 'blur' }
  ],
  arrival: [
    { required: true, message: '请输入到达城市', trigger: 'blur' }
  ],
  date: [
    { required: true, message: '请选择航班日期', trigger: 'change' }
  ],
  price: [
    { required: true, message: '请输入价格', trigger: 'blur' }
  ],
  departureTime: [
    { required: true, message: '请选择起飞时间', trigger: 'change' }
  ],
  arrivalTime: [
    { required: true, message: '请选择到达时间', trigger: 'change' }
  ],
  duration: [
    { required: true, message: '请输入飞行时长', trigger: 'blur' }
  ],
  firstSeat: [
    { required: true, message: '请输入头等舱座位数', trigger: 'blur' }
  ],
  secondSeat: [
    { required: true, message: '请输入商务舱座位数', trigger: 'blur' }
  ],
  thirdSeat: [
    { required: true, message: '请输入经济舱座位数', trigger: 'blur' }
  ]
}

// 获取航班列表
const getAirlineList = async () => {
  try {
    loading.value = true
    const params = {
      departureKeyword: searchForm.departureKeyword,
      arrivalKeyword: searchForm.arrivalKeyword,
      dateKeyword: searchForm.dateKeyword,
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize
    }
    
    const data = await airportApi.getAirlineList(params)
    airlineList.value = data.pageData
    pagination.totalSize = data.totalSize
    pagination.totalPage = data.totalPage
  } catch (error) {
    console.error('获取航班列表失败:', error)
    ElMessage.error('获取航班列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索航班
const searchAirlines = () => {
  pagination.pageNum = 1
  getAirlineList()
}

// 重置搜索
const resetSearch = () => {
  searchForm.departureKeyword = ''
  searchForm.arrivalKeyword = ''
  searchForm.dateKeyword = ''
  pagination.pageNum = 1
  getAirlineList()
}

// 分页处理
const handleSizeChange = (val) => {
  pagination.pageSize = val
  pagination.pageNum = 1
  getAirlineList()
}

const handleCurrentChange = (val) => {
  pagination.pageNum = val
  getAirlineList()
}

// 显示添加对话框
const showAddDialog = () => {
  dialogTitle.value = '添加航班'
  isEditing.value = false
  resetForm()
  dialogVisible.value = true
}

// 编辑航班
const editAirline = (row) => {
  dialogTitle.value = '编辑航班'
  isEditing.value = true
  Object.assign(airlineForm, row)
  dialogVisible.value = true
}

// 删除航班确认
const deleteAirlineConfirm = (row) => {
  ElMessageBox.confirm(
    `确定要删除航班 ${row.departure} → ${row.arrival} 吗？`,
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    deleteAirlineById(row.airlineId)
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 删除航班
const deleteAirlineById = async (id) => {
  try {
    await airportApi.deleteAirline(id)
    ElMessage.success('删除成功')
    getAirlineList()
  } catch (error) {
    console.error('删除航班失败:', error)
    ElMessage.error('删除失败')
  }
}

// 提交表单
const submitForm = () => {
  airlineFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await (isEditing.value 
          ? airportApi.updateAirline(airlineForm)
          : airportApi.addAirline(airlineForm))
        
        ElMessage.success(isEditing.value ? '修改成功' : '添加成功')
        dialogVisible.value = false
        getAirlineList()
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
  Object.assign(airlineForm, {
    airlineId: null,
    airplaneId: null,
    departureAirportId: null,
    arrivalAirportId: null,
    boardingGate: '',
    departure: '',
    arrival: '',
    date: '',
    price: null,
    departureTime: '',
    arrivalTime: '',
    duration: '',
    firstSeat: null,
    secondSeat: null,
    thirdSeat: null
  })
  if (airlineFormRef.value) {
    airlineFormRef.value.clearValidate()
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getAirlineList()
})
</script>

<style scoped>
.airline-management {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #333;
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
</style>



