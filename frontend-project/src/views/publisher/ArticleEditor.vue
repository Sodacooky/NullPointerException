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
          :toolbars="toolbars"
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
export default {
  name: "ArticleEditor",
  data() {
    return {
      editorRawText: "",
      //mavon设置
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: false, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: false, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      },
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
