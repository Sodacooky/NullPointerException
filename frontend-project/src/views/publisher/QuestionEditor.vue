<template>
  <el-container>
    <el-main>

      <el-card>
        <template #header>
          <span style="font-weight: bold;font-size: large">问题信息</span>
        </template>
        <el-input style="font-size: large;" placeholder="用一句话概括你的问题">
          <template #prepend>问题标题</template>
        </el-input>
        <el-input style="font-size: large;margin-top: 4px" placeholder="给问题一个分类帮助有需要的人">
          <template #prepend>问题分类</template>
        </el-input>
      </el-card>

      <el-card style="margin-top: 16px" :body-style="{padding: 0}">
        <template #header>
          <span style="font-weight: bold;font-size: large">正文</span>
        </template>
        <mavon-editor ref="md"
                      placeholder="写写你如何遇到问题、你尝试了什么、贴上相关的代码或者日志"
                      v-model="editorRawText"/>
      </el-card>

      <el-button style="margin-top: 16px" @click="onPublishBtnClick">发布</el-button>

    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "QuestionEditor",
  data() {
    return {
      editorRawText: "",
    }
  }
}
</script>

<style scoped>

</style>