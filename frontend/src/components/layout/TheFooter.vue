<script setup lang="ts">
import { IonCol, IonFooter, IonGrid, IonRow, IonText, IonToolbar } from "@ionic/vue"
</script>

<template>
  <ion-footer class="app-footer">
    <ion-toolbar>
      <ion-grid class="app-footer__grid">
        <ion-row class="ion-align-items-center app-footer__row">
          <ion-col size="12" size-md="5" class="app-footer__col">
            <p class="app-footer__brand">Boutique Hotel Technikum</p>
            <p class="app-footer__note">Vienna, Austria</p>
          </ion-col>
          <ion-col size="12" size-md="2" class="app-footer__col">
            <router-link class="app-footer__link" :to="{ name: 'Imprint' }">Imprint</router-link>
          </ion-col>
          <ion-col size="12" size-md="5" class="app-footer__col">
            <ion-text class="app-footer__note">A calm stay designed for curious travelers.</ion-text>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-toolbar>
  </ion-footer>
</template>

<style scoped>
.app-footer {
  margin-bottom: 0;
}

.app-footer ion-toolbar {
  --background: var(--color-ink);
  --color: #fff;
}

.app-footer__brand {
  font-family: var(--app-font-display);
  font-size: 1rem;
  margin-bottom: 4px;
}

.app-footer__note {
  font-size: 0.85rem;
  color: #f6f1e7;
}

.app-footer__grid {
  --ion-grid-padding: 0px;
  --ion-grid-padding-xs: 0px;
  --ion-grid-padding-sm: 0px;
  --ion-grid-padding-md: 0px;
  --ion-grid-padding-lg: 0px;
  --ion-grid-padding-xl: 0px;
}

.app-footer__row {
  --ion-grid-column-padding: 0px;
}

.app-footer__col {
  text-align: center;
}

.app-footer__link {
  display: inline-block;
  color: #f6f1e7;
  text-decoration: none;
}

.app-footer__link:hover {
  color: #fff;
}

@media (min-width: 768px) {
  .app-footer__col {
    text-align: center;
  }
}
</style>
