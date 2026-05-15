<script setup lang="ts">
import { ref } from "vue"
import { IonButton, IonHeader, IonIcon, IonToolbar } from "@ionic/vue"
import { closeOutline, menuOutline } from "ionicons/icons"
import logo1x from "@/assets/img/logo_with_text.png"

const isMenuOpen = ref(false)

const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value
}

const closeMenu = () => {
  isMenuOpen.value = false
}
</script>

<template>
  <ion-header class="app-header">
    <ion-toolbar>
      <div class="app-header__inner">
        <ion-button
          class="menu-button"
          fill="clear"
          :aria-expanded="isMenuOpen ? 'true' : 'false'"
          aria-label="Toggle navigation"
          @click="toggleMenu"
        >
          <ion-icon :icon="isMenuOpen ? closeOutline : menuOutline" />
        </ion-button>
        <router-link :to="{ name: 'Landing' }" class="app-header__brand" aria-label="Home">
          <img class="app-header__logo" :src="logo1x" alt="Boutique Hotel Technikum" />
        </router-link>
        <nav class="app-header__nav" aria-label="Primary">
          <router-link class="app-header__link" :to="{ name: 'Landing' }">Home</router-link>
          <router-link class="app-header__link" :to="{ name: 'RoomsIndex' }">Rooms</router-link>
          <router-link class="app-header__link" :to="{ name: 'About' }">About</router-link>
        </nav>
      </div>
    </ion-toolbar>
    <div v-if="isMenuOpen" class="app-header__dropdown" role="presentation">
      <nav class="app-header__dropdown-nav" aria-label="Mobile">
        <router-link class="app-header__dropdown-link" :to="{ name: 'Landing' }" @click="closeMenu">
          Home
        </router-link>
        <router-link class="app-header__dropdown-link" :to="{ name: 'RoomsIndex' }" @click="closeMenu">
          Rooms
        </router-link>
        <router-link class="app-header__dropdown-link" :to="{ name: 'About' }" @click="closeMenu">
          About
        </router-link>
      </nav>
    </div>
  </ion-header>
</template>

<style scoped>
.app-header {
  position: sticky;
  top: 0;
  z-index: 10;
  background: transparent;
}

.app-header ion-toolbar {
  --background: linear-gradient(180deg, var(--color-cream) 0%, #f4eadc 100%);
  --color: var(--color-ink);
  box-shadow: 0 8px 20px rgba(31, 27, 24, 0.08);
  border-bottom: 1px solid rgba(31, 27, 24, 0.06);
}

.app-header__inner {
  max-width: 1100px;
  margin: 0 auto;
  width: 100%;
  display: flex;
  align-items: center;
  gap: 28px;
  padding: 10px 16px;
}

.app-header__brand {
  font-family: var(--app-font-display);
  font-size: 1.05rem;
  letter-spacing: 0.01em;
  display: flex;
  align-items: center;
}

.app-header__logo {
  height: 44px;
  width: auto;
  max-width: 220px;
  object-fit: contain;
  display: block;
}

.app-header__nav {
  display: flex;
  gap: 6px;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(31, 27, 24, 0.08);
  border-radius: 999px;
  padding: 4px;
  box-shadow: 0 8px 18px rgba(31, 27, 24, 0.08);
}

.app-header__link {
  color: var(--color-ink);
  text-decoration: none;
  font-weight: 600;
  padding: 6px 14px;
  border-radius: 999px;
  transition: background 0.2s ease, color 0.2s ease;
}

.app-header__link:hover {
  color: var(--color-terracotta);
  background: rgba(183, 101, 60, 0.12);
}

.menu-button {
  display: none;
}

.app-header__dropdown {
  position: absolute;
  left: 0;
  right: 0;
  top: 100%;
  background: var(--color-cream);
  border-bottom: 1px solid rgba(31, 27, 24, 0.08);
  box-shadow: 0 16px 26px rgba(31, 27, 24, 0.12);
}

.app-header__dropdown-nav {
  max-width: 1100px;
  margin: 0 auto;
  padding: 12px 16px 16px;
  display: grid;
  gap: 8px;
}

.app-header__dropdown-link {
  color: var(--color-ink);
  text-decoration: none;
  font-weight: 600;
  padding: 10px 14px;
  border-radius: 14px;
  background: #fff;
  border: 1px solid rgba(31, 27, 24, 0.08);
}

.app-header__dropdown-link:hover {
  color: var(--color-terracotta);
  border-color: rgba(183, 101, 60, 0.28);
}

@media (max-width: 767px) {
  .app-header__nav {
    display: none;
  }

  .menu-button {
    display: flex;
  }

  .app-header__logo {
    height: 36px;
  }
}

@media (min-width: 768px) {
  .app-header__dropdown {
    display: none;
  }
}
</style>
