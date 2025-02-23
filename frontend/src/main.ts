import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

createApp(App).mount('#app')


import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
});

export default api;