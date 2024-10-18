import { defineConfig } from "vite";
import scalaJSPlugin from "@scala-js/vite-plugin-scalajs";

export default defineConfig({
  build: {
    outDir: 'out',
    // lib: {
    //   entry: "./out/extension.js",
    //   formats: ["cjs"],
    //   fileName: "extension",
    // },
    rollupOptions: {
      external: ["vscode"],
    },
    sourcemap: true,
    outDir: "out",
  },
  plugins: [scalaJSPlugin()],
});