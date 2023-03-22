<template>
  <div class="preview-card">
    <div class="title">{{ question_preview_data.title }}</div>
    <div class="text-short">{{ question_preview_data.short }}</div>
    <div class="bottom-bar">
      <span style="float: left"><el-tag type="info">{{ question_preview_data.category }}</el-tag></span>
      <span style="float: right;color: gray">{{ question_preview_data.publish_time }}</span>
    </div>
    <el-divider/>
  </div>
</template>

<script>
export default {
  name: "QuestionPreviewCard",
  props: ["question_preview_data"]
}
</script>

<style scoped>
.preview-card {
  padding: 8px 8px 2px 8px;
}

.preview-card:hover {
  background-color: rgb(248, 248, 248);
}

.preview-card .title {
  font-weight: bold;
}

.preview-card .text-short {
  font-size: small;
  color: gray;
}

.preview-card .bottom-bar {
  margin-top: 2px;
  height: 10px;
}
</style>