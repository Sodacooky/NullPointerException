<template>
  <div class="user-preview-card">
    <el-row>
      <el-col :span="1"></el-col>
      <el-col :span="2">
        <el-image :src="user_preview_data.avatar_url" fit="fill"/>
      </el-col>
      <el-col :span="16" style="padding-left: 8px">
        <div class="nickname">{{ user_preview_data.nickname }}</div>
        <div class="description">{{ user_preview_data.description }}</div>
        <div class="description">注册时间: {{ user_preview_data.register_time }}</div>
        <div class="description">ID: {{ user_preview_data.id }}</div>
      </el-col>
      <el-col :span="1"></el-col>
    </el-row>
    <el-divider/>
  </div>
</template>

<script>
export default {
  name: "UserPreviewCard",
  props: ["user_preview_data"],
  data() {
    return {}
  }

}
</script>

<style scoped>
.user-preview-card {
  padding: 8px 8px 2px 8px;
}

.user-preview-card:hover {
  background-color: rgb(248, 248, 248);
}

.user-preview-card .nickname {
  font-weight: bold;
}

.user-preview-card .description {
  font-size: small;
  color: gray;
}

</style>