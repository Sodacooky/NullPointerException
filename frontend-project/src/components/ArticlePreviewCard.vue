<template>
  <div class="article-preview-card">
    <div class="title">{{ article_preview_data.title }}</div>
    <div class="bottom-bar">
      <span style="float: left"><el-tag type="info">{{ article_preview_data.category }}</el-tag></span>
      <span style="float: right;color: gray">{{ article_preview_data.publish_time }}</span>
    </div>
    <el-divider/>
  </div>
</template>

<script>
export default {
  name: "ArticlePreviewCard",
  props: ["article_preview_data"],
  methods: {},

}
</script>

<style scoped>
.article-preview-card {
  padding: 8px 8px 2px 8px;
}

.article-preview-card:hover {
  background-color: rgb(248, 248, 248);
}

.article-preview-card .title {
  font-weight: bold;
}

.article-preview-card .short {
  font-size: small;
  color: gray;
}

.article-preview-card .bottom-bar {
  margin-top: 2px;
  height: 10px;
}
</style>