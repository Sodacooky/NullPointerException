<template>
  <div>
    <el-container>
      <!--      顶栏-->
      <el-header>
        <el-menu
          :ellipsis="false"
          :router="true"
          :default-active="$route.path"
          mode="horizontal"
        >
          <!--    LOGO-->
          <el-tooltip content="返回首页" placement="bottom">
            <router-link to="/">
              <img alt="website logo" src="/logo.png" width="128" />
            </router-link>
          </el-tooltip>
          <!--    使其他元素靠右的占位器-->
          <div class="flex-grow" />

          <!--    右侧的菜单-->
          <!--未登录用户显示的菜单内容            -->
          <el-menu-item index="/login" v-if="currentUserInfo === null">
            未登录，点击登录
          </el-menu-item>
          <!--已登录用户显示的菜单内容            -->
          <el-menu-item
            index="/publisher/question"
            v-if="currentUserInfo !== null"
          >
            发布问题</el-menu-item
          >
          <el-menu-item
            index="/publisher/article"
            v-if="currentUserInfo !== null"
            >发布文章</el-menu-item
          >
          <el-menu-item index="/notice" v-if="currentUserInfo !== null">
            消息(1)</el-menu-item
          >
          <el-sub-menu index="" v-if="currentUserInfo !== null">
            <template #title>个人相关</template>
            <el-menu-item index="/profile">
              当前用户：{{ currentUserInfo.nickname }}
            </el-menu-item>
            <el-menu-item index="/settings">设置</el-menu-item>
            <el-menu-item index="" @click="doLogout()"> 退出登录</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-header>

      <!--      主体-->
      <el-container>
        <!--          根据需求切换不同的列表显示-->
        <router-view :key="$route.fullPath"></router-view>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { AuthApi } from "@/api/auth";
import { UserApi } from "@/api/user";
import { ElNotification } from "element-plus";

export default {
  name: "UserFrame",
  computed: {},
  data() {
    return {
      currentRoute: "",
      currentUserInfo: null,
    };
  }, //end of data
  methods: {
    doLogout() {
      AuthApi.logout().then((resp) => {
        if (Boolean(resp.data.data) === true) {
          this.$notify({ title: "登出成功", type: "success" });
          localStorage.removeItem("token");
          this.currentUserInfo = null;
          this.$router.replace("/");
        } else {
          ElNotification({ title: "登出失败", type: "error" });
        }
      });
    },
  }, //end of methods
  mounted() {
    //判断当前是否登录
    if (localStorage.getItem("token") === null) return;
    //当然，拥有token不代表就有效
    //判断登录状态，如果登陆了那么加载相关数据，如果没有登录那么移除token
    AuthApi.hasLogin().then((resp) => {
      if (Boolean(resp.data.data)) {
        //已登录，加载用户数据
        UserApi.getCurrentUser().then(
          (resp) => (this.currentUserInfo = resp.data.data)
        );
      } else {
        //否则，清理token
        localStorage.removeItem("token");
      }
    });
  }, //end of mounted
  watch: {
    //监听路由切换，切换路由时检查用户登录状态
    $route() {
      AuthApi.hasLogin().then((resp) => {
        if (Boolean(resp.data.data)) {
          //已登录，加载用户数据
          UserApi.getCurrentUser().then(
            (resp) => (this.currentUserInfo = resp.data.data)
          );
        } else {
          //否则，清理token
          localStorage.removeItem("token");
        }
      });
    },
  }, // watch
};
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}
</style>
