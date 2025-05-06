import { defineStore } from 'pinia'
import { ref } from 'vue'
import axios from 'axios'

export const useChatStore = defineStore('chat', () => {
  const messages = ref([])
  const isLoading = ref(false)
  const error = ref(null)

  const askQuestion = async (question) => {
    isLoading.value = true
    error.value = null
    try {
      // 添加用户消息
      messages.value.push({
        role: 'user',
        content: question
      })
      
      const response = await axios.post('/api/dickSeek/ask', question, {
        headers: {
          'Content-Type': 'text/plain'
        }
      })
      
      // 添加AI回复
      messages.value.push({
        role: 'assistant',
        content: response.data
      })
      
    } catch (err) {
      error.value = err.response?.data?.message || err.message
    } finally {
      isLoading.value = false   
    }
  }

  return { messages, isLoading, error, askQuestion }
})