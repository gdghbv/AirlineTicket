<template>
  <div class="chat-container">
    <div class="chat-header">
      <span class="chat-icon">🤖</span>
      <span class="chat-title">AI 订票助手</span>
    </div>
    <div class="messages">
      <div 
        v-for="(message, index) in messages" 
        :key="index" 
        :class="['message', message.role]"
      >
        <div class="message-content" style="white-space: pre-wrap;">
          <span v-if="message.role==='assistant'" class="msg-avatar assistant-avatar">🤖</span>
          <span v-if="message.role==='user'" class="msg-avatar user-avatar">🧑</span>
          <span>{{ message.content }}</span>
        </div>
      </div>
      <div v-if="isLoading" class="message assistant">
        <div class="message-content"><span class="msg-avatar assistant-avatar">🤖</span>思考中...</div>
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
        <span class="send-icon">➤</span> 发送
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
  max-width: unset;
  width: 100%;
  margin: 0;
  padding: 0 0 8px 0;
  border-radius: 14px;
  background: transparent;
  box-shadow: none;
  border: none;
  position: relative;
  animation: fade-in 1s;
  display: flex;
  flex-direction: column;
  min-height: 700px;
  height: auto;
}
.ai-card-style {
  background: rgba(255,255,255,0.92);
  box-shadow: 0 2px 12px 0 #6a82fb11;
  border-radius: 14px;
  padding: 0 0 8px 0;
  border: none;
  min-height: 320px;
  height: 100%;
  display: flex;
  flex-direction: column;
}
@keyframes fade-in {
  0% { opacity: 0; transform: translateY(30px); }
  100% { opacity: 1; transform: translateY(0); }
}
.chat-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  font-size: 1.08rem;
  font-weight: 700;
  color: #6a82fb;
  letter-spacing: 1px;
}
.chat-icon {
  font-size: 1.5rem;
  animation: ai-bounce 1.2s infinite alternate;
}
@keyframes ai-bounce {
  0% { transform: translateY(0); }
  100% { transform: translateY(-6px) scale(1.08); }
}
.messages {
  margin-bottom: 10px;
  min-height: 200px;
  max-height: none;
  height: auto;
  overflow-y: auto;
  padding: 6px 0;
  background: linear-gradient(120deg, #f8fafc 60%, #e0e7ff 100%);
  border-radius: 10px;
  box-shadow: 0 1.5px 6px 0 #6a82fb08;
}
.message {
  margin-bottom: 15px;
  padding: 0;
  border-radius: 8px;
  display: flex;
  align-items: flex-start;
  animation: msg-in 0.5s;
}
@keyframes msg-in {
  0% { opacity: 0; transform: translateY(20px); }
  100% { opacity: 1; transform: translateY(0); }
}
.message.user {
  justify-content: flex-end;
}
.message.assistant {
  justify-content: flex-start;
}
.message-content {
  display: flex;
  align-items: center;
  gap: 8px;
  background: #e3f2fd;
  color: #333;
  padding: 8px 12px;
  border-radius: 16px;
  box-shadow: 0 1.5px 6px 0 #6a82fb11;
  font-size: 0.98rem;
  max-width: 80vw;
  word-break: break-all;
}
.message.assistant .message-content {
  background: #f5f5f5;
  color: #1976d2;
}
.msg-avatar {
  font-size: 1.3rem;
  margin-right: 4px;
}
.user-avatar {
  color: #6a82fb;
}
.assistant-avatar {
  color: #43e97b;
}
.input-area {
  display: flex;
  gap: 6px;
  margin-top: 0;
}
.input-area input {
  flex-grow: 1;
  padding: 9px 12px;
  border: 1.5px solid #e0e7ef;
  border-radius: 10px;
  font-size: 0.98rem;
  background: #f7fbff;
  transition: border 0.2s;
  outline: none;
}
.input-area input:focus {
  border: 1.5px solid #6a82fb;
  background: #fff;
}
.input-area button {
  padding: 8px 16px;
  background: linear-gradient(90deg, #6a82fb 0%, #fc5c7d 100%);
  color: #fff;
  border: none;
  border-radius: 10px;
  font-size: 0.98rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s, transform 0.15s;
  box-shadow: 0 1.5px 6px 0 #fc5c7d11;
  display: flex;
  align-items: center;
  gap: 6px;
}
.input-area button:hover:not(:disabled) {
  background: linear-gradient(90deg, #fc5c7d 0%, #6a82fb 100%);
  transform: scale(1.06);
}
.input-area button:disabled {
  background: #ccc;
  cursor: not-allowed;
}
.send-icon {
  font-size: 1.1rem;
}
.error-message {
  color: #f44336;
  margin-top: 10px;
  text-align: center;
}
</style>