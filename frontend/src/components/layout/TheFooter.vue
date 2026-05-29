<script setup lang="ts">
import { IonFooter, IonText, IonToolbar } from "@ionic/vue"
import { footerContent } from "@/data/content/layoutContent"
</script>

<template>
  <ion-footer class="app-footer">
    <ion-toolbar class="app-footer__toolbar">
      <div class="app-footer__inner">
        <span class="app-footer__group">
          <span class="app-footer__brand">{{ footerContent.brandName }}</span>
          <span class="app-footer__location">{{ footerContent.location }}</span>
        </span>
        <ion-text class="app-footer__note">{{ footerContent.tagline }}</ion-text>
        <router-link class="app-footer__link" :to="{ name: 'Imprint' }">
          {{ footerContent.imprintLabel }}
        </router-link>
      </div>
    </ion-toolbar>
  </ion-footer>
</template>

<style scoped>
.app-footer {
  margin-bottom: 0;
}

.app-footer__toolbar {
  --background: var(--color-ink);
  --color: #fff;
  --min-height: 32px;
  --padding-top: 4px;
  --padding-bottom: 4px;
  --padding-start: 12px;
  --padding-end: 12px;
}

.app-footer__inner {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  gap: 2px 0;
}

.app-footer__group {
  display: inline-flex;
  align-items: baseline;
  gap: 4px;
  padding: 0 16px;
  border-right: 1px solid rgba(246, 241, 231, 0.25);
}

.app-footer__brand {
  font-family: var(--app-font-display);
  font-size: 0.85rem;
  white-space: nowrap;
}

.app-footer__location {
  font-size: 0.75rem;
  color: #f6f1e7;
  white-space: nowrap;
}

.app-footer__note {
  font-size: 0.75rem;
  color: #f6f1e7;
  white-space: nowrap;
  padding: 0 16px;
  border-right: 1px solid rgba(246, 241, 231, 0.25);
  font-style: italic;
}

.app-footer__link {
  color: #f6f1e7;
  font-size: 0.75rem;
  white-space: nowrap;
  padding-left: 16px;
}

@media (max-width: 767px) {
  .app-footer__group {
    width: 100%;
    justify-content: center;
    border-right: none;
    padding: 0;
  }

  .app-footer__note {
    border-right: none;
    padding: 0 8px 0 0;
  }

  .app-footer__link {
    padding-left: 8px;
    border-left: 1px solid rgba(246, 241, 231, 0.25);
  }
}

.app-footer__link:hover {
  color: #fff;
}
</style>
