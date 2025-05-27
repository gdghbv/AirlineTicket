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
      <el-table :data="airlines" stripe border style="width: 100%" v-loading="loading" :border="true" :fit="true" :resizable="false">
        <el-table-column prop="airlineId" label="航班号" width="90" />
        <el-table-column prop="departure" label="起点" width="90" />
        <el-table-column prop="departureAirportName" label="起飞机场" min-width="120" />
        <el-table-column prop="arrival" label="终点" width="90" />
        <el-table-column prop="arrivalAirportName" label="到达机场" min-width="120" />
        <el-table-column prop="date" label="日期" width="110" />
        <el-table-column prop="departureTime" label="起飞时间" width="90" />
        <el-table-column prop="arrivalTime" label="到达时间" width="90" />
        <el-table-column prop="duration" label="时长" width="80" />
        <el-table-column prop="boardingGate" label="登机口" width="90" />
        <el-table-column label="座位信息" min-width="120" :resizable="false">
          <template #default="{ row }">
            <div style="display: flex; flex-direction: column; gap: 4px; align-items: flex-start;">
              <el-tag size="small" type="success">头等舱: {{ row.firstSeat }}</el-tag>
              <el-tag size="small" type="warning">商务舱: {{ row.secondSeat }}</el-tag>
              <el-tag size="small" type="info">经济舱: {{ row.thirdSeat }}</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="票价(元)" width="100" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="openOrderDialog(row)">购买</el-button>
            <el-button type="primary" size="small" @click="openDetailDialog(row)" style="margin-left:8px;">查看详情</el-button>
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

    <el-dialog v-model="detailDialogVisible" title="航班详情" width="480px" :close-on-click-modal="false">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="航班号">{{ detailData.airlineId }}</el-descriptions-item>
        <el-descriptions-item label="起点">{{ detailData.departure }}</el-descriptions-item>
        <el-descriptions-item label="起飞机场">{{ detailData.departureAirportName }}</el-descriptions-item>
        <el-descriptions-item label="终点">{{ detailData.arrival }}</el-descriptions-item>
        <el-descriptions-item label="到达机场">{{ detailData.arrivalAirportName }}</el-descriptions-item>
        <el-descriptions-item label="日期">{{ detailData.date }}</el-descriptions-item>
        <el-descriptions-item label="起飞时间">{{ detailData.departureTime }}</el-descriptions-item>
        <el-descriptions-item label="到达时间">{{ detailData.arrivalTime }}</el-descriptions-item>
        <el-descriptions-item label="时长">{{ detailData.duration }}</el-descriptions-item>
        <el-descriptions-item label="登机口">{{ detailData.boardingGate }}</el-descriptions-item>
        <el-descriptions-item label="头等舱座位">{{ detailData.firstSeat }}</el-descriptions-item>
        <el-descriptions-item label="商务舱座位">{{ detailData.secondSeat }}</el-descriptions-item>
        <el-descriptions-item label="经济舱座位">{{ detailData.thirdSeat }}</el-descriptions-item>
        <el-descriptions-item label="头等舱票价">{{ detailData.firstSeatPrice }} 元</el-descriptions-item>
        <el-descriptions-item label="商务舱票价">{{ detailData.secondSeatPrice }} 元</el-descriptions-item>
        <el-descriptions-item label="经济舱票价">{{ detailData.thirdSeatPrice }} 元</el-descriptions-item>
        <el-descriptions-item label="起飞机场电话">{{ detailData.departureAirportPhone }}</el-descriptions-item>
        <el-descriptions-item label="降落机场电话">{{ detailData.arrivalAirportPhone }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="orderDialogVisible" title="填写购票信息" width="420px" :close-on-click-modal="false">
      <el-form :model="orderForm" label-width="90px" :rules="orderRules" ref="orderFormRef">
        <el-form-item label="身份证号" prop="citizenId">
          <el-input v-model="orderForm.citizenId" maxlength="18" placeholder="请输入乘机人身份证号" />
        </el-form-item>
        <el-form-item label="乘机人姓名" prop="citizenName">
          <el-input v-model="orderForm.citizenName" maxlength="10" placeholder="请输入乘机人姓名" />
        </el-form-item>
        <el-form-item label="舱型" prop="seatType">
          <el-select v-model="orderForm.seatType" placeholder="请选择舱型" @change="onSeatTypeChange">
            <el-option label="头等舱" value="头等舱" />
            <el-option label="商务舱" value="商务舱" />
            <el-option label="经济舱" value="经济舱" />
          </el-select>
          <el-checkbox v-model="orderForm.useDiscount" style="margin-left:16px;" @change="onDiscountChange">使用优惠</el-checkbox>
        </el-form-item>
        <el-form-item label="实际价格">
          <span v-if="priceInfo.price !== null && priceInfo.price !== undefined">{{ priceInfo.price }} 元 <span v-if="priceInfo.discount && priceInfo.discount > 0">(已优惠 {{ priceInfo.discount }} 元)</span></span>
          <span v-else>请选择舱型</span>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="orderDialogVisible = false">取消</el-button>
        <el-button type="primary" :disabled="!canOrder" @click="handleOrder">确认购买</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
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
  airlineId: '',
  departure: '',
  departureAirportName: '',
  arrival: '',
  arrivalAirportName: '',
  date: '',
  departureTime: '',
  arrivalTime: '',
  duration: '',
  price: '',
  boardingGate: '',
  firstSeat: '',
  secondSeat: '',
  thirdSeat: '',
  departureAirportPhone: '',
  arrivalAirportPhone: '',
  firstSeatPrice: '',
  secondSeatPrice: '',
  thirdSeatPrice: '',
  // ...可补充其它字段
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
    // console.log('完整响应:', res)
    // 直接使用res作为分页数据对象（不再做驼峰转换）
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

// 航班详情弹窗
const detailDialogVisible = ref(false)
const detailData = reactive({})

function openDetailDialog(row) {
  Object.assign(detailData, row)
  detailDialogVisible.value = true
}

// 购票弹窗相关逻辑
const orderDialogVisible = ref(false)
const orderForm = reactive({
  citizenId: '',
  citizenName: '',
  seatType: '',
  useDiscount: false,
})
const priceInfo = reactive({
  price: null,
  discount: 0,
  priceData: {}, // 保存后端返回的所有价格数据
})
const canOrder = computed(() => {
  return !!(
    orderForm.citizenId &&
    orderForm.citizenName &&
    orderForm.seatType &&
    priceInfo.price !== null &&
    priceInfo.price !== undefined
  )
})
let currentOrderAirline = null

function openOrderDialog(row) {
  currentOrderAirline = row
  orderForm.citizenId = ''
  orderForm.citizenName = ''
  orderForm.seatType = ''
  orderForm.useDiscount = false
  priceInfo.price = null
  priceInfo.discount = 0
  priceInfo.priceData = {}
  // 直接传递 row 给 getPrice
  customerApi.getPrice(row).then(res => {
    console.log('getPrice后端返回:', res)
    const data = res || {}
    priceInfo.priceData = data
    // 默认选中经济舱
    orderForm.seatType = '经济舱'
    orderForm.useDiscount = true
    updatePrice()
  })
  orderDialogVisible.value = true
}

function onSeatTypeChange() {
  updatePrice()
}
function onDiscountChange() {
  updatePrice()
}

function updatePrice() {
  const data = priceInfo.priceData
  if (!orderForm.seatType || !data) {
    priceInfo.price = null
    priceInfo.discount = 0
    return
  }
  let price = 0, discount = 0, raw = 0
  if (orderForm.seatType === '头等舱') {
    raw = data.firstSeatPrice
    price = orderForm.useDiscount ? data.firstSeatDiscountPrice : data.firstSeatPrice
    discount = orderForm.useDiscount ? data.firstSeatDiscount : 0
  } else if (orderForm.seatType === '商务舱') {
    raw = data.secondSeatPrice
    price = orderForm.useDiscount ? data.secondSeatDiscountPrice : data.secondSeatPrice
    discount = orderForm.useDiscount ? data.secondSeatDiscount : 0
  } else if (orderForm.seatType === '经济舱') {
    raw = data.thirdSeatPrice
    price = orderForm.useDiscount ? data.thirdSeatDiscountPrice : data.thirdSeatPrice
    discount = orderForm.useDiscount ? data.thirdSeatDiscount : 0
  }
  priceInfo.price = price
  priceInfo.discount = discount
  // 控制台输出当前舱型价格信息
  console.log(`当前舱型: ${orderForm.seatType}, 优惠前: ${raw}, 优惠后: ${price}, 优惠: ${discount}`)
}

const orderRules = {
  citizenId: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { pattern: /^\d{17}[\dX]$/i, message: '身份证号格式不正确', trigger: 'blur' },
  ],
  citizenName: [
    { required: true, message: '请输入乘机人姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '乘机人姓名长度在2到10个字符之间', trigger: 'blur' },
  ],
  seatType: [
    { required: true, message: '请选择舱型', trigger: 'change' },
  ],
}

function handleOrder() {
  // 校验表单
  const order = {
    airlineId: currentOrderAirline.airlineId,
    seatType: orderForm.seatType,
    citizenId: orderForm.citizenId,
    citizenName: orderForm.citizenName,
    spending: priceInfo.price,
    discount: orderForm.useDiscount ? priceInfo.discount : 0
  }
  customerApi.orderAirline(order)
    .then(res => {
      ElMessage.success('购票成功！')
      orderDialogVisible.value = false
      fetchAirlines() // 刷新航班列表
    })
    .catch(err => {
      ElMessage.error('购票失败：' + (err.message || '未知错误'))
    })
}
</script>

<style scoped>
/* 页面整体样式，与登录页面保持一致 */
.ticket-order-root {
  width: 100vw;
  max-width: 100vw;
  min-width: 0;
  margin: 0;
  padding: 80px 20px 32px 20px; /* 顶部留出80px空间，避免被导航栏遮挡 */
  background: linear-gradient(120deg, #e0e7ff 0%, #f8fafc 100%);
  border-radius: 0;
  box-shadow: none;
  min-height: 100vh;
  font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', Arial, sans-serif;
  animation: fade-in-page 0.8s ease-out;
}

/* 页面淡入动画 */
@keyframes fade-in-page {
  0% {
    opacity: 0;
    transform: translateY(20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 搜索框卡片样式 */
.search-box {
  margin-bottom: 24px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 32px;
  box-shadow: 0 8px 32px 0 #6a82fb22, 0 1.5px 6px 0 #fc5c7d11;
  backdrop-filter: blur(18px) saturate(180%);
  border: 1px solid rgba(106, 130, 251, 0.1);
  animation: card-slide-in 0.6s ease-out;
}

/* 卡片滑入动画 */
@keyframes card-slide-in {
  0% {
    opacity: 0;
    transform: translateY(30px) scale(0.95);
  }
  100% {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

/* 搜索表单样式 */
.search-form {
  padding: 20px 0 10px 0;
}

/* 航班列表卡片样式 */
.airline-list-box {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 32px;
  box-shadow: 0 8px 32px 0 #6a82fb22, 0 1.5px 6px 0 #fc5c7d11;
  backdrop-filter: blur(18px) saturate(180%);
  border: 1px solid rgba(106, 130, 251, 0.1);
  margin-bottom: 18px;
  padding-bottom: 20px;
  animation: card-slide-in 0.8s ease-out;
}

/* 表格样式优化 */
.el-table {
  background: transparent;
  border-radius: 16px;
  font-size: 15px;
  overflow: hidden;
}

.el-table th, .el-table td {
  background: transparent;
  border-color: rgba(106, 130, 251, 0.1);
}

.el-table__header th {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%) !important;
  color: white !important;
  font-weight: 600;
  letter-spacing: 0.5px;
}

.el-table__body tr:hover > td {
  background: linear-gradient(90deg, rgba(106, 130, 251, 0.08) 0%, rgba(252, 92, 125, 0.08) 100%) !important;
  transform: scale(1.01);
  transition: all 0.2s ease;
}

/* 表格按钮样式 */
.el-table .el-button--success {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  border: none;
  border-radius: 12px;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(106, 130, 251, 0.3);
  transition: all 0.2s ease;
}

.el-table .el-button--success:hover {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(252, 92, 125, 0.4);
}

.el-table .el-button--primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 12px;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
  transition: all 0.2s ease;
}

.el-table .el-button--primary:hover {
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(118, 75, 162, 0.4);
}

/* 分页栏样式 */
.pagination-bar {
  display: flex;
  justify-content: flex-end;
  margin: 18px 0 0 0;
}

/* 对话框样式 */
.el-dialog {
  border-radius: 32px;
  box-shadow: 0 8px 32px 0 #6a82fb22, 0 1.5px 6px 0 #fc5c7d11;
  backdrop-filter: blur(18px) saturate(180%);
  border: 1px solid rgba(106, 130, 251, 0.1);
}

/* 对话框头部样式 */
.el-dialog__header {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  color: white;
  border-radius: 32px 32px 0 0;
  padding: 20px 24px;
}

.el-dialog__title {
  font-weight: 600;
  font-size: 18px;
  letter-spacing: 0.5px;
}

/* 对话框内容样式 */
.el-dialog__body {
  padding: 24px;
  background: rgba(255, 255, 255, 0.95);
}

/* 表单标签样式 */
.el-form-item__label {
  font-weight: 600;
  color: #6a82fb;
}

/* 输入框样式 */
.el-input__wrapper {
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(106, 130, 251, 0.1);
  border: 1px solid rgba(106, 130, 251, 0.2);
  transition: all 0.2s ease;
}

.el-input__wrapper:hover {
  border-color: #6a82fb;
  box-shadow: 0 4px 12px rgba(106, 130, 251, 0.2);
}

.el-input__wrapper.is-focus {
  border-color: #fc5c7d;
  box-shadow: 0 4px 12px rgba(252, 92, 125, 0.2);
}

/* 选择器样式 */
.el-select .el-input__wrapper {
  border-radius: 12px;
}

/* 日期选择器样式 */
.el-date-editor .el-input__wrapper {
  border-radius: 12px;
}

/* 按钮样式 */
.el-button--primary {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  border: none;
  border-radius: 14px;
  font-weight: 600;
  padding: 12px 24px;
  box-shadow: 0 2px 8px rgba(106, 130, 251, 0.3);
  transition: all 0.2s ease;
}

.el-button--primary:hover {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(252, 92, 125, 0.4);
}

/* 对话框底部按钮样式 */
.el-dialog__footer .el-button {
  border-radius: 14px;
  font-weight: 600;
  padding: 10px 20px;
}

.el-dialog__footer .el-button:not(.el-button--primary) {
  border: 1px solid rgba(106, 130, 251, 0.3);
  color: #6a82fb;
  background: rgba(255, 255, 255, 0.8);
}

.el-dialog__footer .el-button:not(.el-button--primary):hover {
  background: rgba(106, 130, 251, 0.1);
  border-color: #6a82fb;
}

/* 复选框样式 */
.el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: #6a82fb;
  border-color: #6a82fb;
}

.el-checkbox__input.is-checked + .el-checkbox__label {
  color: #6a82fb;
}

/* 响应式设计 */
@media (max-width: 900px) {
  .ticket-order-root {
    padding: 80px 10px 20px 10px;
  }
  
  .search-box,
  .airline-list-box {
    border-radius: 18px;
  }
  
  .el-dialog {
    border-radius: 18px;
  }
  
  .el-dialog__header {
    border-radius: 18px 18px 0 0;
  }
}
</style>