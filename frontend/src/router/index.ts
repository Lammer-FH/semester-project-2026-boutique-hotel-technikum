import { createRouter, createWebHistory } from '@ionic/vue-router'
const LandingPage = () => import('@/pages/LandingPage.vue')
const AboutPage = () => import('@/pages/AboutPage.vue')
const ImprintPage = () => import('@/pages/ImprintPage.vue')
const RoomsPage = () => import('@/pages/RoomsPage.vue')
const BookingConfirmationPage = () => import('@/pages/BookingConfirmationPage.vue')

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
    path: '/rooms',
    name: 'RoomsIndex',
    component: RoomsPage,
  },
  {
    path: '/bookings/:bookingId/confirmation',
    name: 'BookingConfirmation',
    component: BookingConfirmationPage,
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
  scrollBehavior() {
    return { top: 0 }
  },
})

export default router
