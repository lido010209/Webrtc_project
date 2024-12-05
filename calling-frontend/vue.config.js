const { defineConfig } = require('@vue/cli-service')
const endpoints = ["/users", "/token", "/static"];
const proxyConfig = endpoints.reduce((acc, ep)=>{
  acc[ep]={
    target: "http://127.0.0.1:8080",
    changeOrigin: true,
    pathRewrite: {[`^${ep}`]: ep },
  };
  return acc;
}, {});

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy:proxyConfig
  }
})
