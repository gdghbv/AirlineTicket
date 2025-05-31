<template>
  <el-card>
    <h2>个人信息</h2>
    <el-descriptions :column="1" border v-if="userInfo">
      <el-descriptions-item label="用户ID">{{ userInfo.customerId }}</el-descriptions-item>
      <el-descriptions-item label="用户名">{{ userInfo.userName }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{ userInfo.phone }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ userInfo.email }}</el-descriptions-item>
      <el-descriptions-item label="会员积分">
        <template #default>
          <template v-if="userInfo.milsPoints === -1">
            <el-button type="primary" size="small" @click="registerMember">注册会员</el-button>
          </template>
          <template v-else>
            {{ userInfo.milsPoints }}
          </template>
        </template>
      </el-descriptions-item>
    </el-descriptions>
    <div v-else>加载中...</div>
    <el-button type="primary" style="margin-top: 18px;" @click="editDialogVisible = true">修改个人信息</el-button>
    <el-dialog v-model="editDialogVisible" title="修改个人信息" width="380px" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.userName" maxlength="20" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" maxlength="40" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEdit">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>
<script setup>
import { ref, onMounted, reactive, watch } from 'vue'
import { ElMessage } from 'element-plus'

import { getCustomerInfo, updateCustomerInfo ,memberRegister} from '@/api/customer'
const userInfo = ref(null)
const editDialogVisible = ref(false)
const editForm = reactive({ userName: '', email: '' })
onMounted(() => {
  getCustomerInfo().then(res => {
    userInfo.value = res
    console.log('后端返回的个人信息数据:', res)
  }).catch(() => {
    ElMessage.error('获取个人信息失败')
  })
})
watch(editDialogVisible, (val) => {
  if (val && userInfo.value) {
    editForm.userName = userInfo.value.userName
    editForm.email = userInfo.value.email
  }
})
function registerMember() {
  // 这里可以调用会员注册API，或弹窗提示
memberRegister().then(() => {
    ElMessage.success('会员注册成功')
    userInfo.value.milsPoints = 0 //注册后积分为0
    editDialogVisible.value = false
  }).catch(() => {
    ElMessage.error('会员注册失败，请稍后再试')
  })
  ElMessage.info('请前往会员注册页面或调用注册接口')
}
function submitEdit() {
  updateCustomerInfo({
    ...userInfo.value,
    userName: editForm.userName,
    email: editForm.email
  }).then(() => {
    ElMessage.success('修改成功')
    userInfo.value.userName = editForm.userName
    userInfo.value.email = editForm.email
    editDialogVisible.value = false
  }).catch(() => {
    ElMessage.error('修改失败')
  })
}
</script>
