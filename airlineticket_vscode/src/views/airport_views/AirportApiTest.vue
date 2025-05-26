<template>
  <div style="padding: 32px;">
    <h2>机场接口测试页面</h2>
    <el-row :gutter="16" style="margin-bottom: 16px;">
      <el-col :span="6">
        <el-button type="primary" @click="getAirportInfo">获取当前用户信息</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="getAirlineList">查询航班列表</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="addAirline">添加航班</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="updateAirline">修改航班</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="16" style="margin-bottom: 16px;">
      <el-col :span="6">
        <el-button type="primary" @click="deleteAirline">删除航班</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="getAllAirportUsers">查询所有管理员</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="getAllCustomers">查询所有客户</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="getAllAirplanes">查询所有飞机</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="16" style="margin-bottom: 16px;">
      <el-col :span="6">
        <el-button type="primary" @click="addAirplane">添加飞机</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="updateAirplane">修改飞机</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="deleteAirplane">删除飞机</el-button>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="airportLogin">机场登录</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import * as airportApi from '@/api/airport'
import { getAirportLogin } from '@/api/index'
import { setToken } from '@/utils/token-utils'
import { ElMessage } from 'element-plus'

// 示例参数，可根据实际情况修改
const testAirline = {
  airplaneId: 1,
  arrivalAirportId: 1,
  departureAirportId: 3,
  boardingGate: "D12",
  price: 1599.00,
  departureTime: "20:00:00",
  arrivalTime: "23:30:00",
  date: "2025-12-17",
  firstSeat: 25,
  secondSeat: 160,
  thirdSeat: 220,
  duration: "3h30m",
  departure: "广州",
  arrival: "北京"
}
const testAirlineUpdate = {
  airlineId: 1,
  ...testAirline
}
const testAirlineId = 1

const testAirplane = {
  airplaneName: "测试飞机",
  status: "准备中",
  location: "上海",
  firstSeat: 1,
  secondSeat: 1,
  thirdSeat: 1
}
const testAirplaneUpdate = {
  airplaneId: 1,
  ...testAirplane
}
const testAirplaneId = 1

function getAirportInfo() {
  airportApi.getAirportInfo().then(res => {
    console.log('getAirportInfo:', res)
    ElMessage.success('已打印到控制台')
  })
}
function getAirlineList() {
  airportApi.getAirlineList({ pageNum: 1, pageSize: 5 }).then(res => {
    console.log('getAirlineList:', res)
    ElMessage.success('已打印到控制台')
  })
}
function addAirline() {
  airportApi.addAirline(testAirline).then(res => {
    console.log('addAirline:', res)
    ElMessage.success('已打印到控制台')
  })
}
function updateAirline() {
  airportApi.updateAirline(testAirlineUpdate).then(res => {
    console.log('updateAirline:', res)
    ElMessage.success('已打印到控制台')
  })
}
function deleteAirline() {
  airportApi.deleteAirline(testAirlineId).then(res => {
    console.log('deleteAirline:', res)
    ElMessage.success('已打印到控制台')
  })
}
function getAllAirportUsers() {
  airportApi.getAllAirportUsers().then(res => {
    console.log('getAllAirportUsers:', res)
    ElMessage.success('已打印到控制台')
  })
}
function getAllCustomers() {
  airportApi.getAllCustomers().then(res => {
    console.log('getAllCustomers:', res)
    ElMessage.success('已打印到控制台')
  })
}
function getAllAirplanes() {
  airportApi.getAllAirplanes({ pageNum: 1, pageSize: 5 }).then(res => {
    console.log('getAllAirplanes:', res)
    ElMessage.success('已打印到控制台')
  })
}
function addAirplane() {
  airportApi.addAirplane(testAirplane).then(res => {
    console.log('addAirplane:', res)
    ElMessage.success('已打印到控制台')
  })
}
function updateAirplane() {
  airportApi.updateAirplane(testAirplaneUpdate).then(res => {
    console.log('updateAirplane:', res)
    ElMessage.success('已打印到控制台')
  })
}
function deleteAirplane() {
  airportApi.deleteAirplane(testAirplaneId).then(res => {
    console.log('deleteAirplane:', res)
    ElMessage.success('已打印到控制台')
  })
}
function airportLogin() {
  // 示例账号密码，可根据实际情况修改
  getAirportLogin({ phone: '1234', password: '1234' }).then(res => {
    console.log('getAirportLogin:', res.token)
    setToken(res.token)
    ElMessage.success('已打印到控制台')
  })
}


// watch(() => userInfoStore.token, (newVal, oldVal) => {
//   console.log('token变化了，当前值为：', newVal)
// })
</script>
