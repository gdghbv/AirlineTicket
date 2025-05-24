<template>
  <div class="test-customer-api">
    <h2>Customer API 测试页面</h2>
    <div class="api-list">
      <div v-for="api in apis" :key="api.name" class="api-item">
        <el-button type="primary" @click="callApi(api)">{{ api.label }}</el-button>
        <span class="api-desc">{{ api.desc }}</span>
      </div>
    </div>
    <div class="result-box">
      <h3>返回结果：</h3>
      <el-input type="textarea" :rows="12" v-model="result" readonly />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import * as customerApi from '@/api/customer'
import { ElMessage } from 'element-plus'

const result = ref('')

const apis = [
  {
    name: 'askAIQuestion',
    label: 'AI问答',
    desc: '向AI提问，参数info: { question: "..." }',
    params: { question: '你好，AI！' },
  },
  {
    name: 'getAirlineList',
    label: '获取航班列表',
    desc: '参数: { page: 1, size: 5 }',
    params: { page: 1, size: 5 },
  },
  {
    name: 'getCustomerInfo',
    label: '获取个人信息',
    desc: '无参数',
    params: undefined,
  },
  {
    name: 'updateCustomerInfo',
    label: '修改个人信息',
    desc: '参数: { name: "新名字" }（请先获取个人信息再测试）',
    params: { name: '新名字' },
  },
  {
    name: 'getCustomerOrderList',
    label: '查询历史订单',
    desc: '参数: { page: 1, size: 5 }',
    params: { pageSize: 1, pageNum: 1 },
  },
  {
    name: 'orderAirline',
    label: '订购机票',
    desc: '参数: { order: {...}, useDiscount: false }（请根据实际航班信息填写）',
    params: { order: { airlineId: 1, seatType: '经济舱' }, useDiscount: false },
  },
  {
    name: 'cancelOrder',
    label: '退订机票',
    desc: '参数: { orderId: 1 }（请根据实际订单填写）',
    params: { orderId: 1 },
  },
]

async function callApi(api) {
  result.value = '请求中...'
  try {
    let res
    if (api.params !== undefined) {
      res = await customerApi[api.name](api.params)
    } else {
      res = await customerApi[api.name]()
    }
    // axios返回有data，request返回直接是data
    result.value = JSON.stringify(res.data || res, null, 2)
    console.log(api.name + ' 返回：', res.data || res)
    ElMessage.success('请求成功')
  } catch (e) {
    result.value = e.message || JSON.stringify(e)
    ElMessage.error('请求失败')
    console.error(api.name + ' 错误：', e)
  }
}
</script>

<style scoped>
.test-customer-api {
  max-width: 700px;
  margin: 40px auto;
  background: #181f2a;
  border-radius: 16px;
  box-shadow: 0 4px 32px #0ff2  ;
  padding: 32px 24px;
  color: #fff;
  font-family: 'JetBrains Mono', 'Consolas', monospace;
}
.api-list {
  display: flex;
  flex-direction: column;
  gap: 18px;
  margin-bottom: 32px;
}
.api-item {
  display: flex;
  align-items: center;
  gap: 16px;
}
.api-desc {
  color: #8be9fd;
  font-size: 14px;
}
.result-box {
  margin-top: 32px;
}
.result-box h3 {
  margin-bottom: 8px;
  color: #50fa7b;
}
.el-input__inner, .el-textarea__inner {
  background: #232b3b;
  color: #fff;
  border: 1px solid #444c5e;
  font-family: inherit;
}
</style>
