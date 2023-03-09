<template>
  <div class="question-preview-card">
    <div class="title">{{ title }}</div>
    <div class="short">{{ short }}</div>
    <div class="bottom-bar">
      <span style="float: left"><el-tag type="info">{{ category }}</el-tag></span>
      <span style="float: right;color: gray">{{ publish_time }}</span>
    </div>
    <el-divider/>
  </div>
</template>

<script>
export default {
  name: "QuestionPreviewCard",
  props: ["title", "short", "category", "publish_time", "question_id"]
}
</script>

<style scoped>
.question-preview-card {
  padding: 8px 8px 2px 8px;
}

.question-preview-card:hover {
  background-color: rgb(248, 248, 248);
}

.question-preview-card .title {
  font-weight: bold;
}

.question-preview-card .short {
  font-size: small;
  color: gray;
}

.question-preview-card .bottom-bar {
  margin-top: 2px;
  height: 10px;
}
</style>