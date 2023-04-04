<template>
  <!--  侧边菜单-->
    <el-aside style="padding-left: 16px" width="10%">
        <el-menu mode="vertical">
            <el-menu-item index="0">问题管理</el-menu-item>
            <el-menu-item index="1">文章管理</el-menu-item>
            <el-menu-item index="2">问题回答管理</el-menu-item>
            <el-menu-item index="3">文章回复管理</el-menu-item>
            <el-menu-item index="4">用户管理</el-menu-item>
        </el-menu>
    </el-aside>

    <el-main>
        <router-view></router-view>
    </el-main>
</template>

<script>
export default {
    name: "MenuFrame",
};
</script>

<style scoped></style>
