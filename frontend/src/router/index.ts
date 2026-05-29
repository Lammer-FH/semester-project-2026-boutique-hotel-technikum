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
    meta: {
      title: 'Boutique Hotel Technikum',
    },
  },
  {
    path: '/about',
    name: 'About',
    component: AboutPage,
    meta: {
      title: 'About | Boutique Hotel Technikum',
    },
  },
  {
    path: '/rooms',
    name: 'RoomsIndex',
    component: RoomsPage,
    meta: {
      title: 'Rooms | Boutique Hotel Technikum',
    },
  },
  {
    path: '/bookings/:bookingId/confirmation',
    name: 'BookingConfirmation',
    component: BookingConfirmationPage,
    meta: {
      title: 'Booking confirmation | Boutique Hotel Technikum',
    },
  },
  {
    path: '/imprint',
    name: 'Imprint',
    component: ImprintPage,
    meta: {
      title: 'Imprint | Boutique Hotel Technikum',
    },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  },
})

router.afterEach((to) => {
  const title = to.meta?.title as string | undefined
  if (title) {
    document.title = title
  }
})

export default router
