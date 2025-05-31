<template>
    <div class="chat-container">
      <div class="messages">
        <div 
          v-for="(message, index) in messages" 
          :key="index" 
          :class="['message', message.role]"
        >
          <div class="message-content" style="white-space: pre-wrap;">{{ message.content }}</div>
        </div>
        <div v-if="isLoading" class="message assistant">
          <div class="message-content">思考中...</div>
        </div>
      </div>
      
      <form @submit.prevent="handleSubmit" class="input-area">
        <input
          v-model="inputMessage"
          type="text"
          placeholder="输入您的订票相关问题..."
          :disabled="isLoading"
        />
        <button type="submit" :disabled="isLoading || !inputMessage.trim()">
        发送
        </button>
      </form>
      
      <div v-if="error" class="error-message">{{ error }}</div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { useChatStore } from '@/stores/chat'
  import { storeToRefs } from 'pinia'
  
  const chatStore = useChatStore()
  const inputMessage = ref('')

  
  const { messages,  isLoading,error } = storeToRefs(chatStore)
  
  const handleSubmit = async() => {
    if (inputMessage.value.trim()) {
      await chatStore.askQuestion(inputMessage.value)
      inputMessage.value = ''
    }
  }
  </script>
  
  <style scoped>
  .chat-container {
    max-width: 1400px;
    width: 100%;
    margin: 0 auto;
    padding: 28px 24px 20px 24px;
    border: 3px solid;
    border-image: linear-gradient(90deg, #6a9bff 0%, #377dff 60%, #fff 100%) 1;
    border-radius: 18px;
    background: rgba(255,255,255,0.98);
    box-shadow: 0 2px 24px #377dff22;
    max-height: 900px;
    display: flex;
    flex-direction: column;
  }
  
  .messages {
    margin-bottom: 20px;
    min-height: 350px;
    max-height: 600px;
    overflow-y: auto;
    padding: 18px 18px 10px 18px;
    background: #f4f8ff;
    border-radius: 10px;
    box-shadow: 0 1.5px 6px 0 #377dff11;
    flex: 1 1 auto;
  }
  
  .message {
    margin-bottom: 15px;
    padding: 12px 18px;
    border-radius: 10px;
    font-size: 1.08rem;
  }
  
  .message.user {
    background: #e3f2fd;
    margin-left: 12%;
    text-align: right;
  }
  
  .message.assistant {
    background: #f5faff;
    margin-right: 12%;
  }
  
  .input-area {
    display: flex;
    gap: 10px;
    margin-top: 8px;
    flex-shrink: 0;
  }
  
  .input-area input {
    flex-grow: 1;
    padding: 12px 14px;
    border: 1.5px solid #6a9bff44;
    border-radius: 8px;
    font-size: 1.08rem;
    background: #f7fbff;
    transition: border 0.2s;
    outline: none;
  }
  
  .input-area input:focus {
    border: 1.5px solid #377dff;
    background: #fff;
  }
  
  .input-area button {
    padding: 10px 28px;
    background: linear-gradient(90deg, #6a9bff 0%, #377dff 100%);
    color: #fff;
    border: none;
    border-radius: 8px;
    font-size: 1.08rem;
    font-weight: 600;
    cursor: pointer;
    box-shadow: 0 1.5px 6px 0 #377dff11;
    transition: background 0.18s, color 0.18s;
  }
  
  .input-area button:disabled {
    background: #ccc;
    cursor: not-allowed;
  }
  
  .error-message {
    color: #f44336;
    margin-top: 10px;
  }
  </style>