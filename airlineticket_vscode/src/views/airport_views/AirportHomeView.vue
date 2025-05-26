<template>
  
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { House, Menu, User } from '@element-plus/icons-vue'

const route = useRoute()
const activeMenu = ref(route.path)
</script>

<style scoped>
.common-layout {
  height: 100vh;
}
.el-aside {
  background: #f5f7fa;
  min-height: 100vh;
  box-shadow: 2px 0 8px #e0e7ef33;
}
.el-menu {
  border-right: none;
}
</style>