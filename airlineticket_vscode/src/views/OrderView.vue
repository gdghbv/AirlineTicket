<template>
  <NavBar />
  <div class="order-root fade-in-page">
    <h2 class="order-title">订购机票</h2>
    <div class="order-search-bar">
      <input v-model="search.departure" placeholder="出发地" />
      <input v-model="search.arrival" placeholder="目的地" />
      <input v-model="search.date" type="date" />
      <button @click="filterFlights">搜索</button>
    </div>
    <div class="flight-list">
      <div v-for="flight in pagedFlights" :key="flight.id" class="flight-card">
        <div class="flight-info">
          <div><b>{{ flight.departure }}</b> → <b>{{ flight.arrival }}</b></div>
          <div>航班号：{{ flight.code }}</div>
          <div>起飞时间：{{ flight.time }}</div>
          <div>价格：<span class="price">￥{{ flight.price }}</span></div>
        </div>
        <button class="order-btn" @click="orderFlight(flight)">订购</button>
      </div>
      <div v-if="filteredFlights.length === 0" class="no-flights">暂无符合条件的航班</div>
    </div>
    <div v-if="totalPages > 1" class="pagination-bar">
      <button :disabled="currentPage===1" @click="goPrevFive">上五页</button>
      <button :disabled="currentPage===1" @click="goPage(currentPage-1)">上一页</button>
      <span v-for="page in totalPages" :key="page">
        <button :class="{active: page===currentPage}" @click="goPage(page)">{{page}}</button>
      </span>
      <button :disabled="currentPage===totalPages" @click="goPage(currentPage+1)">下一页</button>
      <button :disabled="currentPage===totalPages" @click="goNextFive">下五页</button>
    </div>
    <div class="order-nav-btns">
      <button @click="$router.push('/orders')">订单查询</button>
      <button @click="$router.push('/user')">个人中心</button>
      <button @click="$router.push('/')">返回首页</button>
    </div>
  </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue'
import { ref, computed } from 'vue'
const search = ref({ departure: '', arrival: '', date: '' })
const flights = ref([
  { id: 1, code: 'CA1234', departure: '北京', arrival: '上海', time: '2025-05-10 08:00', price: 880 },
  { id: 2, code: 'MU5678', departure: '上海', arrival: '广州', time: '2025-05-10 13:30', price: 780 },
  { id: 3, code: 'CZ9012', departure: '北京', arrival: '广州', time: '2025-05-11 09:20', price: 980 },
  { id: 4, code: 'ZH3456', departure: '深圳', arrival: '北京', time: '2025-05-12 15:00', price: 1080 },
  { id: 5, code: 'CA7890', departure: '上海', arrival: '北京', time: '2025-05-10 19:00', price: 861 },
  { id: 1, code: 'CA1234', departure: '北京', arrival: '上海', time: '2025-05-10 08:00', price: 882 },
  { id: 2, code: 'MU5678', departure: '上海', arrival: '广州', time: '2025-05-10 13:30', price: 783 },
  { id: 3, code: 'CZ9012', departure: '北京', arrival: '广州', time: '2025-05-11 09:20', price: 984 },
  { id: 4, code: 'ZH3456', departure: '深圳', arrival: '北京', time: '2025-05-12 15:00', price: 10560 },
  { id: 5, code: 'CA7890', departure: '上海', arrival: '北京', time: '2025-05-10 19:00', price: 8607 },
  { id: 1, code: 'CA1234', departure: '北京', arrival: '上海', time: '2025-05-10 08:00', price: 8808 },
  { id: 2, code: 'MU5678', departure: '上海', arrival: '广州', time: '2025-05-10 13:30', price: 78010 },
  { id: 3, code: 'CZ9012', departure: '北京', arrival: '广州', time: '2025-05-11 09:20', price: 98011 },
  { id: 4, code: 'ZH3456', departure: '深圳', arrival: '北京', time: '2025-05-12 15:00', price: 108012 },
  { id: 5, code: 'CA7890', departure: '上海', arrival: '北京', time: '2025-05-10 19:00', price: 86013 },
])
const filteredFlights = ref([...flights.value])
const pageSize = ref(5)
const currentPage = ref(1)
const totalPages = computed(() => Math.ceil(filteredFlights.value.length / pageSize.value))

const pagedFlights = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  return filteredFlights.value.slice(start, start + pageSize.value)
})

function goPage(page) {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page
  }
}

function goPrevFive() {
  goPage(currentPage.value - 5 < 1 ? 1 : currentPage.value - 5)
}

function goNextFive() {
  goPage(currentPage.value + 5 > totalPages.value ? totalPages.value : currentPage.value + 5)
}

function filterFlights() {
  filteredFlights.value = flights.value.filter(f => {
    const matchDeparture = !search.value.departure || f.departure.includes(search.value.departure)
    const matchArrival = !search.value.arrival || f.arrival.includes(search.value.arrival)
    const matchDate = !search.value.date || f.time.startsWith(search.value.date)
    return matchDeparture && matchArrival && matchDate
  })
  currentPage.value = 1 // 搜索后重置到第一页
}
function orderFlight(flight) {
  // 这里应判断登录状态，未登录则跳转登录
  alert(`订购成功！\n航班号：${flight.code}\n${flight.departure} → ${flight.arrival}`)
}
</script>

<style scoped>
.order-root {
  min-height: 60vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding: 48px 0;
}
.order-search-bar {
  display: flex;
  gap: 16px;
  margin-bottom: 32px;
}
.order-search-bar input {
  padding: 8px 14px;
  border: 1.5px solid #e0e7ef;
  border-radius: 8px;
  font-size: 15px;
  outline: none;
  background: #f7fbff;
  transition: border 0.2s;
}
.order-search-bar input:focus {
  border: 1.5px solid #6a82fb;
  background: #fff;
}
.order-search-bar button {
  padding: 8px 22px;
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
}
.order-search-bar button:hover {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
}
.flight-list {
  width: 100%;
  max-width: 700px;
  display: flex;
  flex-direction: column;
  gap: 22px;
}
.flight-card {
  background: rgba(255,255,255,0.97);
  border-radius: 14px;
  box-shadow: 0 4px 24px 0 #6a82fb22, 0 1.5px 6px 0 #fc5c7d11;
  padding: 24px 32px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  transition: box-shadow 0.18s, transform 0.18s, background 0.18s;
  animation: fade-in 0.7s;
}
.flight-card:hover {
  background: linear-gradient(90deg, #f8fafc 60%, #e0e7ff 100%);
  box-shadow: 0 8px 32px 0 #fc5c7d33;
  transform: translateY(-2px) scale(1.02);
}
.flight-info {
  display: flex;
  flex-direction: column;
  gap: 6px;
  font-size: 1.08rem;
}
.price {
  color: #fc5c7d;
  font-weight: 700;
  font-size: 1.1rem;
}
.order-btn {
  background: linear-gradient(90deg, #6a82fb 0%, #43e97b 100%);
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 10px 28px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s, transform 0.15s;
  box-shadow: 0 2px 8px #43e97b22;
}
.order-btn:hover {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
  transform: scale(1.06);
  box-shadow: 0 4px 16px 0 #fc5c7d33;
}
.no-flights {
  color: #bbb;
  font-size: 1.1rem;
  text-align: center;
  margin-top: 32px;
}
.order-nav-btns {
  display: flex;
  gap: 18px;
  margin: 32px 0 0 0;
}
.order-nav-btns button {
  padding: 8px 22px;
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
}
.order-nav-btns button:hover {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
}
.fade-in-page {
  animation: fade-in-page 0.8s cubic-bezier(.68,-0.55,.27,1.55);
}
@keyframes fade-in-page {
  0% { opacity: 0; transform: translateY(40px) scale(0.98); }
  100% { opacity: 1; transform: translateY(0) scale(1); }
}
.order-title {
  font-size: 2rem;
  font-weight: 700;
  color: #6a82fb;
  margin-bottom: 28px;
  letter-spacing: 2px;
  text-shadow: 0 2px 12px #6a82fb22;
  animation: fade-in-title 0.7s;
}
@keyframes fade-in-title {
  0% { opacity: 0; transform: translateY(20px); }
  100% { opacity: 1; transform: translateY(0); }
}
.pagination-bar {
  display: flex;
  gap: 8px;
  justify-content: center;
  align-items: center;
  margin: 24px 0 0 0;
}
.pagination-bar button {
  padding: 6px 14px;
  border-radius: 8px;
  border: none;
  background: #f0f4ff;
  color: #6a82fb;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.18s, color 0.18s;
}
.pagination-bar button.active, .pagination-bar button:hover:not(:disabled) {
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  color: #fff;
}
.pagination-bar button:disabled {
  background: #eee;
  color: #aaa;
  cursor: not-allowed;
}
</style>
