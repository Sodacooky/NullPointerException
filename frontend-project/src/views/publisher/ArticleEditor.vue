<template>
  <el-container>
    <el-main>
      <el-card>
        <template #header>
          <span style="font-weight: bold; font-size: large">文章信息</span>
        </template>
        <el-input placeholder="文章需要一个标题" style="font-size: large">
          <template #prepend>文章标题</template>
        </el-input>
        <el-input
          placeholder="文章需要一个分类"
          style="font-size: large; margin-top: 4px"
        >
          <template #prepend>文章分类</template>
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
          placeholder="你总得写点什么"
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
  name: "ArticleEditor",
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
  methods: {
    onPublishBtnClick() {
      console.log(this.$refs.md.d_render);
    },
  },
};
</script>

<style scoped></style>
