import axios from "axios";
import router from "@/router";

export const baseUrl = "http://localhost:8080";

const requests = axios.create({ baseURL: baseUrl });

// 添加请求拦截器
requests.interceptors.request.use(
  function (config) {
    //请求发送前
    //检查是否有Token储存着，如果有那么添加到Header
    if (config.url.startsWith("/admin")) {
      //请求管理员接口
      if (localStorage.getItem("adminToken") !== null) {
        config.headers.AdminAuthorization = localStorage.getItem("adminToken");
      }
    } else {
      //请求普通用户接口
      if (localStorage.getItem("token") !== null) {
        config.headers.Authorization = localStorage.getItem("token");
      }
    }
    return config;
  },
  function (error) {
    //请求发送失败
    return Promise.reject(error);
  }
);

// 添加响应拦截器
requests.interceptors.response.use(
  function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    if (error.response.status === 498) {
      //如果是498，那么访问的端口需要token而你缺少token或者token过期，鉴权失败
      console.log("Unauthorized detected, redirecting...");
      if (String(error.response.url).startsWith("/admin")) {
        //如果访问的是管理员接口，清除管理员Token，并重定向到管理员登录
        localStorage.removeItem("adminToken");
        router.push("/admin/login");
      } else {
        //否则，清除用户Token，并跳到用户登录
        localStorage.removeItem("token");
        router.push("/login");
      }
    }

    // 对响应错误做点什么
    return Promise.reject(error);
  }
);

export default requests;
