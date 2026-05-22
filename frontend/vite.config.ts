import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { fileURLToPath, URL } from "node:url";

// https://vite.dev/config/
// Enables Vue support and sets '@' as a shortcut for the 'src' directory to simplify imports
export default defineConfig({
    plugins: [vue()],
    server: {
        port: 5000,
    },
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
});
