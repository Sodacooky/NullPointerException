<template>
  <h1>公告编辑</h1>
  <!--添加按钮-->
  <div class="operation-area">
    <el-row>
      <el-col :span="2">
        <el-button :icon="Plus" type="primary">添加</el-button>
      </el-col>
    </el-row>
  </div>
  <!--展示目前已有公告与操作-->
  <div class="announcement-list" style="margin-top: 16px">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between">
          <span
            >公告标题
            <span style="margin-left: 4px; color: gray; font-size: small">
              2023-03-03 11:11:11
            </span>
          </span>
          <span>
            <el-button type="danger">删除 </el-button>
          </span>
        </div>
      </template>
      公告正文内容
    </el-card>
  </div>
</template>

<script>
import { Plus } from "@element-plus/icons-vue";

export default {
  name: "announceEdit",
  computed: {
    Plus() {
      return Plus;
    },
  },
};
</script>

<style scoped></style>
