/**
 * 登录相关的接口
 */
import requests from "@/api/requests";

export class AuthApi {
  /**
   * 用户是否登录
   * @returns {Promise<axios.AxiosResponse<any>>|Promise<unknown>}
   */
  static hasLogin() {
    if (localStorage.getItem("token") === null) {
      //如果没有token那肯定没有登录
      return new Promise((resolve) => {
        //模拟一个没有登录的请求结果
        resolve({ resp: { data: { data: false } } });
      });
    }
    return requests.get("/user-service/auth/hasLogin");
  }

  /**
   * 一般用户登录
   * @param email 邮箱
   * @param password 密码
   * @returns {Promise<axios.AxiosResponse<any>>} data为token或者null
   */
  static login(email, password) {
    if (localStorage.getItem("token") !== null) {
      //如果储存着有token，移除后再登录
      localStorage.removeItem("token");
    }
    return requests.get("/user-service/auth/login");
  }

  /**
   * 一般用户登出
   * @returns {Promise<axios.AxiosResponse<any>>} data为true/false
   */
  static logout() {
    if (localStorage.getItem("token") !== null) {
      //如果储存着有token，移除
      localStorage.removeItem("token");
    }
    return requests.get("/user-service/auth/logout");
  }
}
