import { createRouter, createWebHistory } from '@ionic/vue-router'
import LandingPage from '@/pages/LandingPage.vue'
import AboutPage from '@/pages/AboutPage.vue'
import ImprintPage from '@/pages/ImprintPage.vue'

const routes = [
  {
    path: '/',
    name: 'Landing',
    component: LandingPage,
  },
  {
    path: '/about',
    name: 'About',
    component: AboutPage,
  },
  {
    path: '/imprint',
    name: 'Imprint',
    component: ImprintPage,
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
