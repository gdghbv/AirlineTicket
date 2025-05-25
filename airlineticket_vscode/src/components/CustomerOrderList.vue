<template>
  <el-card>
    <h2>历史订单</h2>
    <el-table :data="orderList" v-loading="orderLoading" style="width:100%">
      <el-table-column prop="orderId" label="订单号" width="100" />
      <el-table-column prop="airlineId" label="航班号" width="100" />
      <el-table-column prop="seatType" label="舱型" width="80" />
      <el-table-column prop="citizenName" label="乘机人" width="100" />
      <el-table-column prop="spending" label="消费金额" width="100" />
      <el-table-column prop="bookingTime" label="下单时间" width="180" />
      <el-table-column prop="orderStat" label="状态" width="100" />
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button
            v-if="scope.row.orderStat === '已支付'"
            type="danger"
            size="small"
            @click="openRefundDialog(scope.row)"
          >退款</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="orderList.length === 0 && !orderLoading" style="margin:20px 0;color:#888;">暂无历史订单</div>
    <el-dialog
      v-model="refundDialogVisible"
      title="确认退款"
      width="340px"
      :close-on-click-modal="false"
    >
      <div>确定要对订单号 <b>{{ refundOrder?.orderId }}</b> 进行退款吗？</div>
      <template #footer>
        <el-button @click="refundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmRefund" :loading="refundLoading">确定</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import * as customerApi from '@/api/customer'
const orderList = ref([])
const orderLoading = ref(false)
const refundDialogVisible = ref(false)
const refundOrder = ref(null)
const refundLoading = ref(false)
function fetchOrderList() {
  orderLoading.value = true
  customerApi.getCustomerOrderList({ pageNum: 1, pageSize: 20 }).then(res => {
    orderList.value = Array.isArray(res.pageData) ? res.pageData : []
  }).catch(() => {
    ElMessage.error('获取历史订单失败')
  }).finally(() => {
    orderLoading.value = false
  })
}
function openRefundDialog(row) {
  refundOrder.value = row
  refundDialogVisible.value = true
}
function confirmRefund() {
  if (!refundOrder.value) return
  refundLoading.value = true
  customerApi.cancelOrder(refundOrder.value)
    .then(() => {
      ElMessage.success('退款成功')
      refundDialogVisible.value = false
      fetchOrderList()
    })
    .catch(() => {
      ElMessage.error('退款失败')
    })
    .finally(() => {
      refundLoading.value = false
    })
}
onMounted(fetchOrderList)
</script>
