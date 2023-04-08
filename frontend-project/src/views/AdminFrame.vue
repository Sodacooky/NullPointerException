<template>
  <el-container>
    <el-aside width="160px">
      <el-menu :unique-opened="true" :router="true">
        <el-image src="/logo.png" width="128" />
        <el-menu-item>
          <el-icon><Close /></el-icon>退出系统
        </el-menu-item>
        <el-sub-menu index="/admin/content/">
          <template #title>
            <el-icon><Files /></el-icon>内容管理
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
            <el-icon><Service /></el-icon>举报处理
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
            <el-icon><Setting /></el-icon>杂项
          </template>
          <el-menu-item index="/admin/misc/announce">公告编辑</el-menu-item>
          <el-menu-item index="/admin/misc/ad">推广编辑</el-menu-item>
          <el-menu-item index="/admin/misc/notice">通知发送</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "AdminFrame",
};
</script>

<style scoped></style>
