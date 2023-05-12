<template>
  <h1>公告编辑</h1>
  <!--添加按钮-->
  <div class="operation-area">
    <el-row>
      <el-col :span="2">
        <el-button :icon="Plus" type="primary" @click="isShowAddDialog = true">
          添加
        </el-button>
      </el-col>
    </el-row>
  </div>
  <!--展示目前已有公告与操作-->
  <div class="announcement-list" style="margin-top: 16px">
    <el-card
      v-for="item in announcements"
      :key="item.id"
      style="margin-bottom: 16px"
    >
      <template #header>
        <div style="display: flex; justify-content: space-between">
          <span>
            {{ item.title }}
            <span style="margin-left: 4px; color: gray; font-size: small">
              {{ item.time }}
            </span>
          </span>
          <span>
            <el-button type="danger" @click="doRemove(item.id)">
              删除
            </el-button>
          </span>
        </div>
      </template>
      {{ item.text }}
    </el-card>
  </div>

  <el-dialog v-model="isShowAddDialog">
    <template #header>
      <span style="font-weight: bold">添加公告条目</span>
    </template>
    <el-form label-position="top" label-width="72px">
      <el-form-item label="标题">
        <el-input v-model="dialogDate.title" />
      </el-form-item>
      <el-form-item label="正文">
        <el-input
          type="textarea"
          :autosize="{ minRows: 4, maxRows: 8 }"
          v-model="dialogDate.text"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doAdd()">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { Plus } from "@element-plus/icons-vue";
import { HomeApi } from "@/api/home";
import { AdminMiscApi } from "@/api/adminMisc";

export default {
  name: "announceEdit",
  computed: {
    Plus() {
      return Plus;
    },
  },
  data() {
    return {
      announcements: [],
      isShowAddDialog: false,
      dialogDate: { title: "", text: "" },
    };
  }, //data
  methods: {
    doAdd() {
      AdminMiscApi.addAnnouncement(
        this.dialogDate.title,
        this.dialogDate.text
      ).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success("添加成功");
          this.isShowAddDialog = false;
          this.dialogDate.text = "";
          this.dialogDate.title = "";
          this.loadAnnouncement();
        } else {
          this.$notify.error({
            title: "添加失败",
            message: resp.data.message,
          });
        }
      });
    },
    doRemove: function (announcementId) {
      this.$confirm("删除这条公告？", "确定删除", {
        confirmButtonText: "是的，删除",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          AdminMiscApi.removeAnnouncement(announcementId).then((resp) => {
            if (resp.data.code === "0") {
              this.$notify.success("操作成功");
              this.loadAnnouncement();
            } else {
              this.$notify.error({
                title: "删除失败",
                message: resp.data.message,
              });
            }
          });
        })
        .catch(() => {
          this.$notify.info("操作取消");
        });
    },
    loadAnnouncement() {
      HomeApi.getAnnouncement().then((resp) => {
        this.announcements = resp.data.data;
      });
    },
  }, //methods
  mounted() {
    this.loadAnnouncement();
  },
};
</script>

<style scoped></style>
