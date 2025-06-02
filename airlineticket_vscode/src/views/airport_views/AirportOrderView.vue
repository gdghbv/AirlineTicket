<template>
  <div class="order-management">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>订单管理</h2>
    </div>

    <!-- 搜索区域 -->
    <el-card class="search-card" shadow="never">
      <el-form :model="searchForm" inline>
        <el-form-item label="订单号">
          <el-input v-model="searchForm.orderId" placeholder="请输入订单号" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item label="乘客姓名">
          <el-input v-model="searchForm.citizenName" placeholder="请输入乘客姓名" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item label="客户用户Id">
          <el-input v-model="searchForm.customerId" placeholder="请输入客户用户Id" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item label="航线Id">
          <el-input v-model="searchForm.airlineId" placeholder="请输入航线Id" clearable style="width: 180px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchOrders">
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

    <!-- 订单列表 -->
    <el-card class="table-card" shadow="never">
      <el-table :data="orderList" v-loading="loading" style="width: 100%" stripe class="order-table">
        <el-table-column prop="orderId" label="订单号" width="90" />
        <el-table-column prop="citizenName" label="乘客姓名" width="100" />
        <el-table-column prop="citizenId" label="证件号" width="140" />
        <el-table-column prop="airlineId" label="航班ID" width="80" />
        <el-table-column prop="departure" label="出发地" width="90" />
        <el-table-column prop="arrival" label="目的地" width="90" />
        <el-table-column prop="seatType" label="舱位" width="80" />
        <el-table-column prop="orderStat" label="订单状态" width="100">
          <template #default="scope">
            <el-tag :type="getOrderStatTagType(scope.row.orderStat)" effect="dark">{{ scope.row.orderStat }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="票价" width="90">
          <template #default="scope">
            <span style="color:#00ffe7;font-weight:bold;">¥{{ scope.row.price }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="spending" label="实付" width="90">
          <template #default="scope">
            <span style="color:#00ffe7;">¥{{ scope.row.spending }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="bookingTime" label="下单时间" width="160">
          <template #default="scope">
            {{ formatDateTime(scope.row.bookingTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="showDetail(scope.row)">
              <el-icon><Search /></el-icon>
              详情
            </el-button>
            <el-button size="small" type="primary" @click="editOrder(scope.row)">
              <el-icon><Edit /></el-icon>
              修改
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

    <!-- 订单详情弹窗 -->
    <el-dialog title="订单详情" v-model="detailDialogVisible" width="600px" class="order-detail-dialog">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ detailOrder.orderId }}</el-descriptions-item>
        <el-descriptions-item label="航班ID">{{ detailOrder.airlineId }}</el-descriptions-item>
        <el-descriptions-item label="乘客姓名">{{ detailOrder.citizenName }}</el-descriptions-item>
        <el-descriptions-item label="证件号">{{ detailOrder.citizenId }}</el-descriptions-item>
        <el-descriptions-item label="出发地">{{ detailOrder.departure }}</el-descriptions-item>
        <el-descriptions-item label="目的地">{{ detailOrder.arrival }}</el-descriptions-item>
        <el-descriptions-item label="舱位">{{ detailOrder.seatType }}</el-descriptions-item>
        <el-descriptions-item label="票价">¥{{ detailOrder.price }}</el-descriptions-item>
        <el-descriptions-item label="实付">¥{{ detailOrder.spending }}</el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag :type="getOrderStatTagType(detailOrder.orderStat)">{{ detailOrder.orderStat }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="下单时间">{{ formatDateTime(detailOrder.bookingTime) }}</el-descriptions-item>
        <el-descriptions-item label="登机口">{{ detailOrder.boardingGate }}</el-descriptions-item>
        <el-descriptions-item label="出发时间">{{ detailOrder.departureTime }}</el-descriptions-item>
        <el-descriptions-item label="到达时间">{{ detailOrder.arrivalTime }}</el-descriptions-item>
        <el-descriptions-item label="飞行时长">{{ detailOrder.duration }}</el-descriptions-item>
        <el-descriptions-item label="座位号">{{ detailOrder.seatId }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 订单编辑弹窗 -->
    <el-dialog :title="'修改订单[' + editOrderForm.orderId + ']'" v-model="editDialogVisible" width="500px" class="order-edit-dialog">
      <el-form :model="editOrderForm" label-width="100px">
        <el-form-item label="舱位类型">
          <el-select v-model="editOrderForm.seatType" style="width: 100%">
            <el-option label="头等舱" value="头等舱" />
            <el-option label="商务舱" value="商务舱" />
            <el-option label="经济舱" value="经济舱" />
          </el-select>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-select v-model="editOrderForm.orderStat" style="width: 100%">
            <el-option label="已预订" value="已预订" />
            <el-option label="已支付" value="已支付" />
            <el-option label="已完成" value="已完成" />
            <el-option label="已取消" value="已取消" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitEditOrder">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Refresh, Edit } from '@element-plus/icons-vue'
import * as airportApi from '@/api/airport'

const loading = ref(false)
const orderList = ref([])
const detailDialogVisible = ref(false)
const editDialogVisible = ref(false)
const detailOrder = ref({})
const editOrderForm = reactive({})

const searchForm = reactive({
  orderId: '',
  citizenName: '',
  customerId: '',
  airlineId: '',
  pageNum: 1,
  pageSize: 10
})

const pagination = reactive({
  pageNum: 1,
  pageSize: 10,
  totalSize: 0,
  totalPage: 0
})

const getOrderList = async () => {
  try {
    loading.value = true
    const params = {
      orderId: searchForm.orderId,
      citizenName: searchForm.citizenName,
      customerId: searchForm.customerId,
      airlineId: searchForm.airlineId,
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize
    }
    const res = await airportApi.getAirportOrders(params)
    orderList.value = res.pageData || []
    pagination.totalSize = res.totalSize || 0
    pagination.totalPage = res.totalPage || 0
  } catch (e) {
    ElMessage.error('获取订单失败')
  } finally {
    loading.value = false
  }
}

const searchOrders = () => {
  pagination.pageNum = 1
  getOrderList()
}
const resetSearch = () => {
  searchForm.orderId = ''
  searchForm.citizenName = ''
  searchForm.customerId = ''
  searchForm.airlineId = ''
  pagination.pageNum = 1
  getOrderList()
}
const handleSizeChange = (size) => {
  pagination.pageSize = size
  getOrderList()
}
const handleCurrentChange = (page) => {
  pagination.pageNum = page
  getOrderList()
}
const showDetail = (row) => {
  detailOrder.value = { ...row }
  detailDialogVisible.value = true
}
const editOrder = (row) => {
  Object.assign(editOrderForm, row)
  editDialogVisible.value = true
}
const submitEditOrder = async () => {
  try {
    await airportApi.updateOrder(editOrderForm)
    ElMessage.success('订单修改成功')
    editDialogVisible.value = false
    getOrderList()
  } catch (e) {
    ElMessage.error('订单修改失败')
  }
}
const getOrderStatTagType = (stat) => {
  switch (stat) {
    case '已预订': return 'info'
    case '已支付': return 'success'
    case '已完成': return 'primary'
    case '已取消': return 'danger'
    default: return 'default'
  }
}
const formatDateTime = (dt) => {
  if (!dt) return ''
  return dt.replace('T', ' ')
}
onMounted(() => {
  getOrderList()
})
</script>

<style scoped>
.order-management {
  padding: 24px 24px 24px 24px;
  background: #f7fafc;
  min-height: 100vh;
  max-width: none;
  margin: 0;
  box-sizing: border-box;
}
.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 18px;
}
.page-header h2 {
  color: #3a7afe;
  font-size: 2rem;
  letter-spacing: 2px;
  text-shadow: none;
}
.search-card {
  background: #fff;
  border: 1px solid #e3e8f0;
  box-shadow: 0 2px 12px 0 rgba(58,122,254,0.06);
  margin-bottom: 18px;
}
.table-card {
  background: #fff;
  border: 1px solid #e3e8f0;
  box-shadow: 0 2px 18px 0 rgba(58,122,254,0.08);
  overflow-x: auto;
}
.order-table {
  min-width: 900px;
  width: 100%;
  --el-table-header-bg-color: #f0f4fa;
  --el-table-header-text-color: #3a7afe;
  --el-table-row-hover-bg-color: #eaf3ff;
  --el-table-border-color: #e3e8f0;
  font-size: 15px;
}
.order-table .el-table__cell {
  color: #3a4664;
  background: transparent;
}
.order-table .el-table__row:hover {
  background: #eaf3ff !important;
}
.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 16px;
}
.order-detail-dialog .el-descriptions {
  background: #f7fafc;
  border-radius: 8px;
  border: 1px solid #e3e8f0;
}
.order-detail-dialog .el-descriptions__label {
  color: #3a7afe;
  font-weight: bold;
}
.order-detail-dialog .el-descriptions__content {
  color: #3a4664;
}
.order-edit-dialog .el-form {
  background: #f7fafc;
  border-radius: 8px;
  padding: 12px 0;
}
.order-edit-dialog .el-form-item__label {
  color: #3a7afe;
}
.order-edit-dialog .el-input, .order-edit-dialog .el-select {
  background: #fff;
  color: #3a7afe;
  border: 1px solid #e3e8f0;
}
.dialog-footer {
  text-align: right;
}
</style>