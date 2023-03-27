<template>
  <el-container>
    <!--    顶部菜单-->
    <el-header>
      <el-menu mode="horizontal" :ellipsis="false">
        <!--    LOGO-->
        <img src="/logo.png" alt="website logo" width="128"/>
        <!--    使其他元素靠右的占位器-->
        <div style="flex-grow: 1"/>
        <!--    右侧的菜单-->
        <el-menu-item index="1">内容管理</el-menu-item>
        <el-menu-item index="2">举报处理</el-menu-item>
        <el-menu-item index="3">公告管理</el-menu-item>
        <el-menu-item index="4">用户通知</el-menu-item>
        <el-menu-item index="5">退出管理后台</el-menu-item>
      </el-menu>
    </el-header>
    <!--    内容，包括对应自己的侧边栏菜单-->
    <el-container>
      <router-view></router-view>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "AdminFrame"
}
</script>

<style scoped>

</style>