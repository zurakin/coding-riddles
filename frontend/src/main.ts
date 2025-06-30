import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/router'

createApp(App).use(router).mount('#app')

import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
});

export default api;