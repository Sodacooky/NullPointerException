<template>
  <el-container>
    <el-aside width="160px">
      <el-menu
        :default-active="$route.path"
        :router="true"
        :unique-opened="true"
      >
        <el-tooltip content="返回用户端" placement="right">
          <router-link to="/">
            <img alt="website logo" src="/logo.png" width="128" />
          </router-link>
        </el-tooltip>
        <el-menu-item index="" @click="doLogout()">
          <el-icon>
            <Close />
          </el-icon>
          退出登录
        </el-menu-item>
        <el-sub-menu index="/admin/content/">
          <template #title>
            <el-icon>
              <Files />
            </el-icon>
            内容管理
          </template>
          <el-menu-item index="/admin/content/question">问题管理</el-menu-item>
          <el-menu-item index="/admin/content/answer">回答管理</el-menu-item>
          <el-menu-item index="/admin/content/article">文章管理</el-menu-item>
          <el-menu-item index="/admin/content/articleReply">
            文章回复管理
          </el-menu-item>
          <el-menu-item index="/admin/content/user">用户管理</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="/admin/report/">
          <template #title>
            <el-icon>
              <Service />
            </el-icon>
            举报处理
          </template>
          <el-menu-item index="/admin/report/question">问题举报</el-menu-item>
          <el-menu-item index="/admin/report/answer">回答举报</el-menu-item>
          <el-menu-item index="/admin/report/article">文章举报</el-menu-item>
          <el-menu-item index="/admin/report/articleReply">
            文章回复举报
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="/admin/misc/">
          <template #title>
            <el-icon>
              <Setting />
            </el-icon>
            杂项
          </template>
          <el-menu-item index="/admin/misc/announce">公告编辑</el-menu-item>
          <el-menu-item index="/admin/misc/ad">推广编辑</el-menu-item>
          <el-menu-item index="/admin/misc/notice">通知发送</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-main>
      <div style="min-width: 1000px">
        <router-view></router-view>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { AuthApi } from "@/api/auth";

export default {
  name: "AdminFrame",
  methods: {
    doLogout() {
      //调用登出api
      AuthApi.adminLogout()
        .then((resp) => {
          if (Boolean(resp.data.data)) {
            //成功
            this.$notify({ title: "登出成功", type: "success" });
            this.$router.replace("/admin/"); //强制重定向一下
          } else {
            //失败
            this.$notify({ title: "登出失败", type: "error" });
          }
        })
        .catch((error) => {
          if (error.response.status === 498) {
            this.$notify({
              title: "登出失败",
              message: "你还没登录",
              type: "error",
            });
          }
        });
    },
    /**
     * 检测登录状态并拦截
     */
    checkLoginState() {
      //没有token肯定没登录
      if (localStorage.getItem("adminToken") === null) {
        this.$router.replace("/admin/login");
        return;
      }
      //当然，拥有token不代表就有效
      //判断登录状态，如果登陆了那么加载相关数据，如果没有登录那么移除token
      AuthApi.adminHasLogin().then((resp) => {
        if (!Boolean(resp.data.data)) {
          //清理token，并跳转到登录页面
          localStorage.removeItem("adminToken");
          this.$router.replace("/admin/login");
        }
      });
    },
  }, //methods
  mounted() {
    this.checkLoginState();
  },
  watch: {
    //监听路由切换，切换路由时检查用户登录状态
    $route() {
      this.checkLoginState();
    },
  }, // watch
};
</script>

<style scoped></style>