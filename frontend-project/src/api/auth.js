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
        resolve({ data: { data: false } });
      });
    } else return requests.get("/service-user/auth/hasLogin");
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
    return requests.post("/service-user/auth/login", { email, password });
  }

  /**
   * 一般用户登出，该方法并没有移除token
   * @returns {Promise<axios.AxiosResponse<any>>} data为true/false
   */
  static logout() {
    return requests.get("/service-user/auth/logout");
  }

  /**
   * 注册
   * @param email
   * @param nickname
   * @param password
   * @param confirmPassword
   * @param code
   * @returns {Promise<axios.AxiosResponse<any>>}
   */
  static register(email, nickname, password, confirmPassword, code) {
    return requests.post("/service-user/auth/register", {
      email,
      nickname,
      password,
      confirmPassword,
      code,
    });
  }

  static registerNicknameCheck(nickname) {
    return requests.get("/service-user/auth/registerNicknameCheck", {
      params: { nickname },
    });
  }

  static registerEmailCheck(email) {
    return requests.get("/service-user/auth/registerEmailCheck", {
      params: { email },
    });
  }

  static registerVerify(magic) {
    return requests.get("/service-user/auth/registerVerify", {
      params: { magic },
    });
  }

  static adminHasLogin() {
    if (localStorage.getItem("adminToken") === null) {
      //如果没有token那肯定没有登录
      return new Promise((resolve) => {
        //模拟一个没有登录的请求结果
        resolve({ data: { data: false } });
      });
    } else return requests.get("/service-user/auth/admin/hasLogin");
  }

  static adminLogin(password) {
    return requests.post("/service-user/auth/admin/login", { password });
  }

  static adminLogout() {
    return requests.get("/service-user/auth/admin/logout");
  }
}
