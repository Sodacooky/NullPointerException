<template>
  <div style="padding-left: 5vw; padding-right: 5vw">
    <el-container>
      <!--      顶栏-->
      <el-header>
        <el-menu :ellipsis="false" :router="true" mode="horizontal">
          <!--    LOGO-->
          <el-tooltip content="返回首页" placement="bottom">
            <router-link to="/">
              <img alt="website logo" src="/logo.png" width="128" />
            </router-link>
          </el-tooltip>
          <!--    使其他元素靠右的占位器-->
          <div class="flex-grow" />
          <!--    右侧的菜单-->
          <el-menu-item index="/publisher/question">发布问题</el-menu-item>
          <el-menu-item index="/publisher/article">发布文章</el-menu-item>
          <el-menu-item index="/notice">消息(1)</el-menu-item>
          <el-sub-menu index="/">
            <template #title>我的</template>
            <el-menu-item index="/profile">当前用户：Sodacooky</el-menu-item>
            <el-menu-item index="/settings">设置</el-menu-item>
            <el-menu-item index="/">退出登录</el-menu-item>
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
export default {
  name: "UserFrame",
  data() {
    return {};
  },
};
</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}
</style>
