
import { createPinia } from 'pinia';

import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.mount('#app');

import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
});

export default api;