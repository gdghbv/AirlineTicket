<template>
  <div class="airline-management">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>航班管理</h2>
      <el-button type="primary" @click="addDialogVisible = true" class="add-btn">
        <el-icon><Plus /></el-icon>
        增加航班
      </el-button>
    </div>

    <!-- 搜索区域 -->
    <el-card class="search-card" shadow="never">
      <el-form :model="searchForm" inline>
        <el-form-item label="起点">
          <el-input v-model="searchForm.departureKeyword" placeholder="请输入起点" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item label="终点">
          <el-input v-model="searchForm.arrivalKeyword" placeholder="请输入终点" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item label="起飞日期">
          <el-date-picker v-model="searchForm.dateKeyword" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" clearable style="width: 180px" />
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
      <el-table :data="airlines" v-loading="loading" style="width: 100%" stripe class="airline-table">
        <el-table-column prop="airlineId" label="航班号" width="90" />
        <el-table-column prop="departure" label="起点" width="90" />
        <el-table-column prop="departureAirportName" label="起飞机场" width="240" />
        <el-table-column prop="arrival" label="终点" width="90" />
        <el-table-column prop="arrivalAirportName" label="到达机场" width="240" />
        <el-table-column prop="date" label="日期" width="110" />
        <el-table-column prop="departureTime" label="起飞时间" width="90" />
        <el-table-column prop="arrivalTime" label="到达时间" width="90" />
        <el-table-column prop="duration" label="时长" width="80" />
        <el-table-column prop="price" label="票价(元)" width="100" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="openEditDialog(scope.row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)" style="margin-left:8px;">
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <div class="pagination-wrapper">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :page-size="searchForm.pageSize"
          :current-page="searchForm.pageNum"
          :page-sizes="[7, 10, 20]"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>

    <!-- 增加航班弹窗 -->
    <el-dialog v-model="addDialogVisible" title="增加航班" width="480px" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="110px" ref="addFormRef">
        <el-form-item label="飞机ID" prop="airplaneId">
          <el-input v-model="addForm.airplaneId" />
        </el-form-item>
        <el-form-item label="目的机场ID" prop="arrivalAirportId">
          <el-input v-model="addForm.arrivalAirportId" />
        </el-form-item>
        <el-form-item label="起飞机场ID" prop="departureAirportId">
          <el-input v-model="addForm.departureAirportId" />
        </el-form-item>
        <el-form-item label="登机口" prop="boardingGate">
          <el-input v-model="addForm.boardingGate" />
        </el-form-item>
        <el-form-item label="基础价格" prop="price">
          <el-input v-model="addForm.price" />
        </el-form-item>
        <el-form-item label="起飞时间" prop="departureTime">
          <el-time-picker v-model="addForm.departureTime" placeholder="选择起飞时间" format="HH:mm:ss" value-format="HH:mm:ss" />
        </el-form-item>
        <el-form-item label="降落时间" prop="arrivalTime">
          <el-time-picker v-model="addForm.arrivalTime" placeholder="选择降落时间" format="HH:mm:ss" value-format="HH:mm:ss" />
        </el-form-item>
        <el-form-item label="起飞日期" prop="date">
          <el-date-picker v-model="addForm.date" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="时长" prop="duration">
          <el-input v-model="addForm.duration" />
        </el-form-item>
        <el-form-item label="起点" prop="departure">
          <el-input v-model="addForm.departure" />
        </el-form-item>
        <el-form-item label="终点" prop="arrival">
          <el-input v-model="addForm.arrival" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">确定</el-button>
      </template>
    </el-dialog>

    <!-- 修改航班弹窗 -->
    <el-dialog v-model="editDialogVisible" title="修改航班" width="480px" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="110px" ref="editFormRef">
        <el-form-item label="航班ID" prop="airlineId">
          <el-input v-model="editForm.airlineId" disabled />
        </el-form-item>
        <el-form-item label="飞机ID" prop="airplaneId">
          <el-input v-model="editForm.airplaneId" />
        </el-form-item>
        <el-form-item label="目的机场ID" prop="arrivalAirportId">
          <el-input v-model="editForm.arrivalAirportId" />
        </el-form-item>
        <el-form-item label="起飞机场ID" prop="departureAirportId">
          <el-input v-model="editForm.departureAirportId" />
        </el-form-item>
        <el-form-item label="登机口" prop="boardingGate">
          <el-input v-model="editForm.boardingGate" />
        </el-form-item>
        <el-form-item label="基础价格" prop="price">
          <el-input v-model="editForm.price" />
        </el-form-item>
        <el-form-item label="起飞时间" prop="departureTime">
          <el-time-picker v-model="editForm.departureTime" placeholder="选择起飞时间" format="HH:mm:ss" value-format="HH:mm:ss" />
        </el-form-item>
        <el-form-item label="降落时间" prop="arrivalTime">
          <el-time-picker v-model="editForm.arrivalTime" placeholder="选择降落时间" format="HH:mm:ss" value-format="HH:mm:ss" />
        </el-form-item>
        <el-form-item label="起飞日期" prop="date">
          <el-date-picker v-model="editForm.date" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="时长" prop="duration">
          <el-input v-model="editForm.duration" />
        </el-form-item>
        <el-form-item label="起点" prop="departure">
          <el-input v-model="editForm.departure" />
        </el-form-item>
        <el-form-item label="终点" prop="arrival">
          <el-input v-model="editForm.arrival" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleEdit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus, Search, Refresh, Edit, Delete } from '@element-plus/icons-vue'
import * as airportApi from '@/api/airport'

const searchForm = reactive({
  departureKeyword: '',
  arrivalKeyword: '',
  dateKeyword: '',
  pageNum: 1,
  pageSize: 10,
})
const airlines = ref([])
const total = ref(0)
const loading = ref(false)

function fetchAirlines() {
  loading.value = true
  airportApi.getAirlineList({
    ...searchForm,
    pageNum: searchForm.pageNum,
    pageSize: searchForm.pageSize
  }).then(res => {
    airlines.value = res.pageData || []
    total.value = res.totalSize || 0
  }).catch(() => {
    ElMessage.error('获取航班信息失败')
  }).finally(() => {
    loading.value = false
  })
}

function searchAirlines() {
  searchForm.pageNum = 1
  fetchAirlines()
}
function resetSearch() {
  searchForm.departureKeyword = ''
  searchForm.arrivalKeyword = ''
  searchForm.dateKeyword = ''
  searchForm.pageNum = 1
  fetchAirlines()
}
function handleSizeChange(size) {
  searchForm.pageSize = size
  searchForm.pageNum = 1
  fetchAirlines()
}
function handlePageChange(page) {
  searchForm.pageNum = page
  fetchAirlines()
}

// 增加航班弹窗
const addDialogVisible = ref(false)
const addForm = reactive({
  airplaneId: '',
  arrivalAirportId: '',
  departureAirportId: '',
  boardingGate: '',
  price: '',
  departureTime: '',
  arrivalTime: '',
  date: '',
  duration: '',
  departure: '',
  arrival: ''
})
const addFormRef = ref()
function handleAdd() {
  airportApi.addAirline(addForm).then(() => {
    ElMessage.success('添加成功')
    addDialogVisible.value = false
    fetchAirlines()
  }).catch(() => {
    ElMessage.error('添加失败')
  })
}

// 修改航班弹窗
const editDialogVisible = ref(false)
const editForm = reactive({
  airlineId: '',
  airplaneId: '',
  arrivalAirportId: '',
  departureAirportId: '',
  boardingGate: '',
  price: '',
  departureTime: '',
  arrivalTime: '',
  date: '',
  duration: '',
  departure: '',
  arrival: ''
})
const editFormRef = ref()
function openEditDialog(row) {
  Object.assign(editForm, {
    airlineId: row.airlineId,
    airplaneId: row.airplaneId,
    arrivalAirportId: row.arrivalAirportId,
    departureAirportId: row.departureAirportId,
    boardingGate: row.boardingGate,
    price: row.price,
    departureTime: row.departureTime,
    arrivalTime: row.arrivalTime,
    date: row.date,
    duration: row.duration,
    departure: row.departure,
    arrival: row.arrival
  })
  editDialogVisible.value = true
}
function handleEdit() {
  airportApi.updateAirline(editForm).then(() => {
    ElMessage.success('修改成功')
    editDialogVisible.value = false
    fetchAirlines()
  }).catch(() => {
    ElMessage.error('修改失败')
  })
}

function handleDelete(row) {
  airportApi.deleteAirline(row.airlineId).then(() => {
    ElMessage.success('删除成功')
    fetchAirlines()
  }).catch(() => {
    ElMessage.error('删除失败')
  })
}

onMounted(() => {
  fetchAirlines()
})
</script>

<style scoped>
.airline-management {
  padding: 24px;
  background: #f7fafc;
  min-height: 100vh;
  box-sizing: border-box;
}
.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 18px;
}
.page-header h2 {
  color: #3a7afe;
  font-size: 2rem;
  font-weight: 600;
  margin: 0;
}
.add-btn {
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
.add-btn:hover {
  background: #2466d1;
}
.search-card {
  background: #fff;
  border: 1px solid #e3e8f0;
  box-shadow: 0 2px 12px 0 rgba(58,122,254,0.06);
  margin-bottom: 18px;
}
.table-card {
  width: 100vw;
  max-width: 100vw;
  min-width: 0;
  overflow-x: auto;
  box-sizing: border-box;
  background: #fff;
  border: 1px solid #e3e8f0;
  box-shadow: 0 2px 18px 0 rgba(58,122,254,0.08);
  margin-bottom: 20px;
}
.airline-table {
  min-width: 0;
  width: 100vw;
  max-width: 100vw;
  box-sizing: border-box;
  /* 让表格自适应屏幕宽度 */
}
.airline-table .el-table__cell {
  color: #3a4664;
  background: transparent;
}
.airline-table .el-table__row:hover {
  background: #eaf3ff !important;
}
.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 16px;
}
</style>