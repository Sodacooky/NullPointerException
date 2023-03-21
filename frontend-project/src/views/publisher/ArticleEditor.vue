<template>
  <el-container>
    <el-main>

      <el-card>
        <template #header>
          <span style="font-weight: bold;font-size: large">文章信息</span>
        </template>
        <el-input style="font-size: large;" placeholder="文章需要一个标题">
          <template #prepend>文章标题</template>
        </el-input>
        <el-input style="font-size: large;margin-top: 4px" placeholder="文章需要一个分类">
          <template #prepend>文章分类</template>
        </el-input>
      </el-card>

      <el-card style="margin-top: 16px" :body-style="{padding: 0}">
        <template #header>
          <span style="font-weight: bold;font-size: large">正文</span>
        </template>
        <mavon-editor ref="md" placeholder="你总得写点什么" v-model="editorRawText"/>
      </el-card>

      <el-button style="margin-top: 16px" @click="onPublishBtnClick">发布</el-button>

    </el-main>
  </el-container>
</template>

<script>

export default {
  name: "ArticleEditor",
  data() {
    return {
      editorRawText: "",
    }
  },
  methods: {
    onPublishBtnClick() {
      console.log(this.$refs.md.d_render);
    }
  }
}
</script>

<style scoped>

</style>