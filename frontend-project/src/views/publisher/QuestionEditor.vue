<template>
  <el-container>
    <el-main>
      <el-card>
        <template #header>
          <span style="font-weight: bold; font-size: large">问题信息</span>
        </template>
        <el-input placeholder="用一句话概括你的问题" style="font-size: large">
          <template #prepend>问题标题</template>
        </el-input>
        <el-input
          placeholder="给问题一个分类帮助有需要的人"
          style="font-size: large; margin-top: 4px"
        >
          <template #prepend>问题分类</template>
        </el-input>
      </el-card>

      <el-card :body-style="{ padding: 0 }" style="margin-top: 16px">
        <template #header>
          <span style="font-weight: bold; font-size: large">正文</span>
        </template>
        <mavon-editor
          ref="md"
          v-model="editorRawText"
          :toolbars="mavonToolbars"
          placeholder="写写你如何遇到问题、你尝试了什么、贴上相关的代码或者日志"
        />
      </el-card>

      <el-button style="margin-top: 16px" @click="onPublishBtnClick"
        >发布
      </el-button>
    </el-main>
  </el-container>
</template>

<script>
import { mavonToolbars } from "@/api/mavonSettings";

export default {
  name: "QuestionEditor",
  computed: {
    mavonToolbars() {
      return mavonToolbars;
    },
  },
  data() {
    return {
      editorRawText: "",
    };
  },
};
</script>

<style scoped></style>
