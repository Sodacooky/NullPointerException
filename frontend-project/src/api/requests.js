import axios from "axios";
import router from "@/router";

export const baseUrl = "http://localhost:8080";

const requests = axios.create({baseURL: baseUrl});

// 添加请求拦截器
requests.interceptors.request.use(
    function (config) {
        //请求发送前
        //检查是否有Token储存着，如果有那么添加到Header
        if (config.url.startsWith("/admin")) {
            if (localStorage.getItem("adminToken") !== null) {
                config.headers.Authorization = localStorage.getItem("adminToken");
            }
        } else {
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
            //如果是498，那么访问的端口需要token而你缺少token或者token过期
            localStorage.removeItem("token"); //如果有token那么就是过期的，清除他
            //根据访问的是用户页面还是管理页面，重定向对应的登录页面
            if (error.response.url.startsWith("/admin")) router.push("/admin/login");
            else router.push("/login");
        }

        // 对响应错误做点什么
        return Promise.reject(error);
    }
);

export default requests;
