import pluginVue from "eslint-plugin-vue"
import vueTsEslintConfig from "@vue/eslint-config-typescript"
import eslintConfigPrettier from "eslint-config-prettier"

export default [
  {
    ignores: ["dist/**", "node_modules/**", "src/**/*.d.ts"],
  },
  ...pluginVue.configs["flat/essential"],
  ...vueTsEslintConfig(),
  eslintConfigPrettier,
  {
    rules: {
      "vue/multi-word-component-names": "off",
      "vue/no-unused-vars": "warn",
      "@typescript-eslint/no-unused-vars": [
        "warn",
        { argsIgnorePattern: "^_", varsIgnorePattern: "^_" },
      ],
      "@typescript-eslint/no-explicit-any": "warn",
      "no-console": "warn",
    },
  },
]
