<template>
  <h1>通知发送</h1>
  <div class="container">
    <el-form label-position="top">
      <el-form-item label="标题">
        <el-input placeholder="输入通知的标题" />
      </el-form-item>
      <el-form-item label="接收者">
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4 }"
          placeholder="可以输入多个用户ID或用户昵称，以空格间隔"
        />
      </el-form-item>
      <el-form-item label="正文">
        <el-input
          type="textarea"
          :autosize="{ minRows: 8, maxRows: 16 }"
          placeholder="通知内容的正文"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary">发送</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "noticeSend",
};
</script>

<style scoped></style>
