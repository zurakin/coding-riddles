import { createRouter, createWebHistory } from 'vue-router';
import type { Component } from 'vue';
import RiddlePage from '../pages/RiddlePage.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: RiddlePage as Component
  },
  {
    path: '/riddle/:id',
    name: 'Riddle',
    component: RiddlePage as Component
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
