<template>
  <!--空状态    -->
  <el-empty v-if="noticeData.length < 1" description="没有消息"></el-empty>

  <!--手风琴折叠消息列表    -->
  <el-collapse
    :accordion="true"
    v-for="item in noticeData"
    :key="item.id"
    v-model="activeNoticeName"
    @change="onActiveNoticeChange()"
  >
    <!--每一条      -->
    <el-collapse-item :name="item.id">
      <!--标题，如果未读有标志-->
      <template #title>
        <div style="font-weight: bold; font-size: large">
          <span v-if="item.isRead === 0">
            <el-tag type="info">未读</el-tag>
          </span>
          {{ item.title }}
        </div>
      </template>
      <!--正文        -->
      <div class="main-text">
        {{ item.text }}
      </div>
      <!--时间戳        -->
      <div class="notice-info" style="color: gray; text-align: right">
        {{ item.time }}
      </div>
    </el-collapse-item>
  </el-collapse>
</template>

<script>
import { NoticeApi } from "@/api/notice";

export default {
  name: "SystemNoticeView",
  data() {
    return {
      noticeData: [],
      activeNoticeName: "",
    };
  },
  methods: {
    onActiveNoticeChange() {
      //发送标记已读请求
      NoticeApi.read(this.activeNoticeName).then((resp) => {
        if (resp.data.code === 0) {
          //成功则也将本地的标记为已读
          let found = this.noticeData.find((value) => {
            return value.id === this.activeNoticeName;
          });
          found.isRead = 1;
        }
      });
    },
  },
  mounted() {
    NoticeApi.getSystem().then((resp) => {
      this.noticeData = resp.data.data;
    });
  }, //mounted
};
</script>

<style scoped></style>
