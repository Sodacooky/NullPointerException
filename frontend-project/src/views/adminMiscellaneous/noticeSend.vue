<template>
  <h1>通知发送</h1>
  <div class="container">
    <el-form label-position="top" style="min-width: 500px; width: 75%">
      <el-form-item label="标题">
        <el-input placeholder="输入通知的标题" v-model="title" />
      </el-form-item>
      <el-form-item label="接收者">
        <el-input
          :autosize="{ minRows: 2, maxRows: 4 }"
          placeholder="可以输入多个用户ID或用户昵称，以空格间隔"
          type="textarea"
          v-model="goalUserIds"
        />
      </el-form-item>
      <el-form-item label="正文">
        <el-input
          :autosize="{ minRows: 8, maxRows: 16 }"
          placeholder="通知内容的正文"
          type="textarea"
          v-model="text"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="send()">发送</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { AdminMiscApi } from "@/api/adminMisc";

export default {
  name: "noticeSend",
  data() {
    return {
      title: "",
      goalUserIds: "",
      text: "",
    };
  },
  methods: {
    send() {
      AdminMiscApi.sendCustomNotice(
        this.goalUserIds,
        this.title,
        this.text
      ).then((resp) => {
        if (resp.data.code !== "0") {
          this.$notify.error({ title: "发送失败", message: resp.data.message });
          return;
        }
        this.title = "";
        this.goalUserIds = "";
        this.text = "";
        this.$notify.success({ title: "操作完成", message: resp.data.message });
      });
    },
  },
};
</script>

<style scoped></style>
