<template>
  <div class="ticket-order-root">
    <CustomerNavBar />
    <el-card class="search-box">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="起点">
          <el-input v-model="searchForm.departureKeyword" placeholder="请输入起点" clearable />
        </el-form-item>
        <el-form-item label="终点">
          <el-input v-model="searchForm.arrivalKeyword" placeholder="请输入终点" clearable />
        </el-form-item>
        <el-form-item label="起飞日期">
          <el-date-picker v-model="searchForm.dateKeyword" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchAirlines">搜索</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="airline-list-box">
      <el-table :data="airlines" stripe border style="width: 100%" v-loading="loading">
        <el-table-column prop="airline_id" label="航班号" width="90" />
        <el-table-column prop="departure" label="起点" width="90" />
        <el-table-column prop="departure_airport_name" label="起飞机场" min-width="120" />
        <el-table-column prop="arrival" label="终点" width="90" />
        <el-table-column prop="arrival_airport_name" label="到达机场" min-width="120" />
        <el-table-column prop="date" label="日期" width="110" />
        <el-table-column prop="departure_time" label="起飞时间" width="90" />
        <el-table-column prop="arrival_time" label="到达时间" width="90" />
        <el-table-column prop="duration" label="时长" width="80" />
        <el-table-column prop="price" label="票价(元)" width="100" />
        <el-table-column label="操作" width="110">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="openOrderDialog(row)">购买</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-bar">
        <el-pagination
          background
          layout="sizes, prev, pager, next, jumper"
          :total="total"
          :page-size="searchForm.pageSize"
          :current-page="searchForm.pageNum"
          :page-sizes="[7, 10, 20]"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>

    <el-dialog v-model="orderDialogVisible" title="填写乘客信息" width="420px" :close-on-click-modal="false">
      <el-form :model="orderForm" label-width="90px" :rules="orderRules" ref="orderFormRef">
        <el-form-item label="身份证号" prop="citizenId">
          <el-input v-model="orderForm.citizenId" maxlength="18" placeholder="请输入乘客身份证号" />
        </el-form-item>
        <el-form-item label="乘客姓名" prop="citizenName">
          <el-input v-model="orderForm.citizenName" maxlength="10" placeholder="请输入乘客姓名" />
        </el-form-item>
        <el-form-item label="座位类型" prop="seatType">
          <el-select v-model="orderForm.seatType" placeholder="请选择座位类型">
            <el-option label="头等舱" value="头等舱" />
            <el-option label="商务舱" value="商务舱" />
            <el-option label="经济舱" value="经济舱" />
          </el-select>
        </el-form-item>
        <el-form-item label="积分折扣">
          <el-switch v-model="orderForm.useDiscount" active-text="使用" inactive-text="不使用" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="orderDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitOrder">确认购买</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import * as customerApi from '@/api/customer'
import CustomerNavBar from '@/components/CustomerNavBar.vue'

const searchForm = reactive({
  departureKeyword: '',
  arrivalKeyword: '',
  dateKeyword: '',
  pageNum: 1,
  pageSize: 10,
})
const airlines = ref([{
  airline_id:'',
  departure:'',
  departure_airport_name:'',
  arrival:'',
  arrival_airport_name:'',
  date:'',
  departure_time:'',
  arrival_time:'',
  duration:'',  
  price:'',
  
}])
const total = ref(0)
const loading = ref(false)

function fetchAirlines() {
  loading.value = true
  customerApi.getAirlineList({
    ...searchForm,
    pageNum: searchForm.pageNum,
    pageSize: searchForm.pageSize
  }).then(res => {
    console.log('完整响应:', res)
    // 直接使用res作为分页数据对象
    if (Array.isArray(res.pageData)) {
      airlines.value = res.pageData
    } else {
      airlines.value = []
    }
    total.value = res.totalSize || 0
  }).catch((err) => {
    console.error('获取航班信息失败:', err)
    ElMessage.error('获取航班信息失败')
  }).finally(() => {
    loading.value = false
  })
}

fetchAirlines()

function handleSizeChange(size) {
  searchForm.pageSize = size
  searchForm.pageNum = 1
  fetchAirlines()
}
function handlePageChange(page) {
  searchForm.pageNum = page
  fetchAirlines()
}

// 购票弹窗
const orderDialogVisible = ref(false)
const orderForm = reactive({
  citizenId: '',
  citizenName: '',
  seatType: '',
  useDiscount: false,
})
const orderFormRef = ref()
const orderRules = {
  citizenId: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { min: 15, max: 18, message: '身份证号格式不正确', trigger: 'blur' }
  ],
  citizenName: [
    { required: true, message: '请输入乘客姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名格式不正确', trigger: 'blur' }
  ],
  seatType: [
    { required: true, message: '请选择座位类型', trigger: 'change' }
  ]
}
let currentAirlineId = null
function openOrderDialog(row) {
  currentAirlineId = row.airline_id
  orderForm.citizenId = ''
  orderForm.citizenName = ''
  orderForm.seatType = ''
  orderForm.useDiscount = false
  orderDialogVisible.value = true
}
function submitOrder() {
  orderFormRef.value.validate(async valid => {
    if (!valid) return
    try {
      await customerApi.orderAirline({
        airlineId: currentAirlineId,
        seatType: orderForm.seatType,
        citizenId: orderForm.citizenId,
        citizenName: orderForm.citizenName
      }, orderForm.useDiscount)
      ElMessage.success('购票成功！')
      orderDialogVisible.value = false
      fetchAirlines()
    } catch (e) {
      ElMessage.error(e?.response?.data?.message || '购票失败')
    }
  })
}
</script>

<style scoped>
.ticket-order-root {
  width: 100vw;
  max-width: 100vw;
  min-width: 0;
  margin: 0;
  padding: 80px 0 32px 0; /* 顶部留出80px空间，避免被导航栏遮挡 */
  background: linear-gradient(120deg, #e0e7ff 0%, #f8fafc 100%);
  border-radius: 0;
  box-shadow: none;
  min-height: 100vh;
}
.search-box {
  margin-bottom: 24px;
  background: linear-gradient(90deg, #e0fff7 0%, #e0e7ff 100%);
  border-radius: 18px;
  box-shadow: 0 2px 12px #6a82fb22;
}
.search-form {
  padding: 12px 0 0 0;
}
.airline-list-box {
  background: linear-gradient(90deg, #fff 0%, #e0e7ff 100%);
  border-radius: 18px;
  box-shadow: 0 2px 12px #6a82fb22;
  margin-bottom: 18px;
  padding-bottom: 8px;
}
.el-table {
  background: transparent;
  border-radius: 12px;
  font-size: 15px;
}
.el-table th, .el-table td {
  background: transparent;
}
.el-table__header th {
  background: #e0e7ff !important;
}
.el-table__body tr:hover > td {
  background: #e6fff6 !important;
}
.pagination-bar {
  display: flex;
  justify-content: flex-end;
  margin: 18px 0 0 0;
}
.el-dialog {
  border-radius: 18px;
}
</style>