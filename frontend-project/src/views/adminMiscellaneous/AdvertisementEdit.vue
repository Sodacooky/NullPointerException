<template>
  <h1>推广编辑</h1>
  <!--添加按钮-->
  <div class="operation-area">
    <el-row>
      <el-col :span="2">
        <el-button :icon="Plus" type="primary">添加</el-button>
      </el-col>
    </el-row>
  </div>
  <!--展示目前已有推广与操作-->
  <div class="announcement-list" style="margin-top: 16px">
    <el-row>
      <el-col :span="8" style="padding: 16px 16px 16px 0">
        <el-card :body-style="{ padding: '0' }">
          <template #header>
            <div style="display: flex; justify-content: space-between">
              <span>添加于：2023-03-03 12:12:12</span>
              <span>
                <el-button type="danger">删除 </el-button>
              </span>
            </div>
          </template>
          <el-image fit="fill" src="/testpic1.jpg"></el-image>
        </el-card>
      </el-col>

      <el-col :span="8" style="padding: 16px 16px 16px 0">
        <el-card :body-style="{ padding: '0' }">
          <template #header>
            <div style="display: flex; justify-content: space-between">
              <span>添加于：2023-03-15 12:12:12</span>
              <span>
                <el-button type="danger">删除 </el-button>
              </span>
            </div>
          </template>
          <el-image fit="fill" src="/testpic2.jpg"></el-image>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { Plus } from "@element-plus/icons-vue";

export default {
  name: "AdvertisementEdit",
  computed: {
    Plus() {
      return Plus;
    },
  },
};
</script>

<style scoped></style>