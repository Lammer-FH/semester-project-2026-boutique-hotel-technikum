<script setup lang="ts">
import { computed, ref, watch } from "vue"
import { IonButton, IonHeader, IonIcon, IonToolbar } from "@ionic/vue"
import { closeOutline, menuOutline } from "ionicons/icons"
import { useRoute } from "vue-router"
import { navigationContent } from "@/data/content/layoutContent"

const isMenuOpen = ref(false)
const route = useRoute()

const currentRouteName = computed(() => route.name)

const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value
}

const closeMenu = () => {
  isMenuOpen.value = false
}

watch(
  () => route.fullPath,
  () => {
    closeMenu()
  }
)
</script>

<template>
  <ion-header class="app-header">
    <ion-toolbar>
      <div class="app-header__inner">
        <ion-button
          class="menu-button"
          fill="clear"
          :aria-expanded="isMenuOpen ? 'true' : 'false'"
          :aria-label="navigationContent.menuToggleLabel"
          @click="toggleMenu"
        >
          <ion-icon :icon="isMenuOpen ? closeOutline : menuOutline" />
        </ion-button>
        <router-link
          :to="{ name: 'Landing' }"
          class="app-header__brand"
          :aria-label="navigationContent.homeLabel"
        >
          <img class="app-header__logo" src="/logo.svg?v=3" :alt="navigationContent.brandAlt" />
        </router-link>
        <nav class="app-header__nav" :aria-label="navigationContent.primaryNavLabel">
          <router-link
            class="app-header__link"
            :to="{ name: 'Landing' }"
            :aria-current="currentRouteName === 'Landing' ? 'page' : undefined"
          >
            {{ navigationContent.homeLabel }}
          </router-link>
          <router-link
            class="app-header__link"
            :to="{ name: 'RoomsIndex' }"
            :aria-current="currentRouteName === 'RoomsIndex' ? 'page' : undefined"
          >
            {{ navigationContent.roomsLabel }}
          </router-link>
          <router-link
            class="app-header__link"
            :to="{ name: 'About' }"
            :aria-current="currentRouteName === 'About' ? 'page' : undefined"
          >
            {{ navigationContent.aboutLabel }}
          </router-link>
        </nav>
      </div>
    </ion-toolbar>
    <div v-if="isMenuOpen" class="app-header__dropdown" role="presentation">
      <nav class="app-header__dropdown-nav" :aria-label="navigationContent.mobileNavLabel">
        <router-link
          class="app-header__dropdown-link"
          :to="{ name: 'Landing' }"
          :aria-current="currentRouteName === 'Landing' ? 'page' : undefined"
          @click="closeMenu"
        >
          {{ navigationContent.homeLabel }}
        </router-link>
        <router-link
          class="app-header__dropdown-link"
          :to="{ name: 'RoomsIndex' }"
          :aria-current="currentRouteName === 'RoomsIndex' ? 'page' : undefined"
          @click="closeMenu"
        >
          {{ navigationContent.roomsLabel }}
        </router-link>
        <router-link
          class="app-header__dropdown-link"
          :to="{ name: 'About' }"
          :aria-current="currentRouteName === 'About' ? 'page' : undefined"
          @click="closeMenu"
        >
          {{ navigationContent.aboutLabel }}
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
  --padding-start: 0;
  --padding-end: 0;
  box-shadow: 0 8px 20px rgba(31, 27, 24, 0.08);
  border-bottom: 1px solid rgba(31, 27, 24, 0.06);
}

.app-header__inner {
  max-width: var(--site-max-width);
  margin: 0 auto;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  padding: 6px 16px;
  position: relative;
}

.app-header__brand {
  font-family: var(--app-font-display);
  font-size: 1.05rem;
  letter-spacing: 0.01em;
  display: flex;
  align-items: center;
}

.app-header__logo {
  height: 42px;
  width: auto;
  max-width: 300px;
  object-fit: contain;
  display: block;
}

.app-header__nav {
  display: flex;
  gap: 6px;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(31, 27, 24, 0.08);
  border-radius: 999px;
  padding: 3px;
  box-shadow: 0 8px 18px rgba(31, 27, 24, 0.08);
}

.app-header__link {
  color: var(--color-ink);
  text-decoration: none;
  font-weight: 600;
  padding: 4px 12px;
  border-radius: 999px;
  transition: background 0.2s ease, color 0.2s ease;
}

.app-header__link:hover {
  color: var(--color-terracotta);
  background: rgba(183, 101, 60, 0.12);
}

.menu-button {
  display: none;
  align-items: center;
  justify-content: center;
  padding: 6px;
  border-radius: 10px;
  color: var(--color-terracotta);
  --color: var(--color-terracotta);
  transition: transform 0.12s ease;
}

.menu-button ion-icon {
  font-size: 26px;
  line-height: 1;
  color: inherit;
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
  max-width: var(--site-max-width);
  margin: 0 auto;
  padding: 8px 16px 10px;
  display: grid;
  gap: 6px;
}

.app-header__dropdown-link {
  color: var(--color-ink);
  text-decoration: none;
  font-weight: 600;
  padding: 8px 14px;
  border-radius: 14px;
  background: #fff;
  border: 1px solid rgba(31, 27, 24, 0.08);
}

.app-header__dropdown-link:hover {
  color: var(--color-terracotta);
  border-color: rgba(183, 101, 60, 0.28);
}

@media (max-width: 767px) {
  .app-header__inner {
    min-height: 48px;
  }

  .app-header__nav {
    display: none;
  }

  .menu-button {
    display: flex;
    position: absolute;
    right: 0;
    top: 50%;
    transform: translateY(-50%);
    padding: 6px;
  }

  .menu-button ion-icon {
    font-size: 26px;
  }

  .app-header__brand {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
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
