<template>
  <h1>推广编辑</h1>
  <!--添加按钮-->
  <div class="operation-area">
    <el-row>
      <el-col :span="2">
        <el-button :icon="Plus" type="primary" @click="openAddDialog()">
          添加
        </el-button>
      </el-col>
    </el-row>
  </div>
  <!--展示目前已有推广与操作-->
  <div class="announcement-list" style="margin-top: 16px">
    <el-row>
      <el-col
        :span="8"
        style="padding: 16px 16px 16px 0"
        v-for="item in adsList"
        :key="item.id"
      >
        <el-card :body-style="{ padding: '0' }">
          <template #header>
            <div style="display: flex; justify-content: space-between">
              <span>添加于：{{ item.time }}</span>
              <span>
                <el-button type="danger" @click="doRemoveAdv(item.id)">
                  删除
                </el-button>
              </span>
            </div>
          </template>
          <el-tooltip placement="top" :content="item.url">
            <el-image fit="fill" :src="HomeApi.getAdsImageUrl(item.image)" />
          </el-tooltip>
        </el-card>
      </el-col>
    </el-row>
  </div>

  <!--添加条目对话框    -->
  <el-dialog v-model="isShowAddDialog">
    <template #header
      ><span style="font-weight: bold">添加推广条目</span>
    </template>
    <el-form label-position="top" label-width="72px">
      <el-form-item label="目标跳转URL">
        <el-input v-model="addDialogData.url" placeholder="实在没有写个井号" />
      </el-form-item>
      <el-form-item label="图片文件名">
        <el-input
          v-model="addDialogData.image"
          placeholder="上传图片后自动填写"
        >
          <template #append>
            <el-upload
              :action="AdminMiscApi.getAdvImageUploadUrl()"
              :headers="uploadHeaders"
              :limit="1"
              :show-file-list="false"
              :before-upload="onBeforeImageUpload"
              :on-success="onImageUploadSuccess"
            >
              点击上传
            </el-upload>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="图片预览">
        <el-image
          v-if="
            addDialogData.image !== undefined && addDialogData.image.length > 0
          "
          :src="HomeApi.getAdsImageUrl(addDialogData.image)"
        />
        <span v-else><el-tag type="info">没有图片</el-tag></span>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="doAddAdv()">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import { Plus } from "@element-plus/icons-vue";
import { HomeApi } from "@/api/home";
import { AdminMiscApi } from "@/api/adminMisc";

export default {
  name: "AdvertisementEdit",
  computed: {
    AdminMiscApi() {
      return AdminMiscApi;
    },
    HomeApi() {
      return HomeApi;
    },
    Plus() {
      return Plus;
    },
  },
  data() {
    return {
      adsList: [],
      isShowAddDialog: false,
      addDialogData: {},
      uploadHeaders: {},
    };
  }, //data
  methods: {
    openAddDialog() {
      if (this.adsList.length >= 8) {
        this.$notify.warning({
          title: "不推荐超过八个推广",
          message: "如果仍想上传多于8个请使用其他方式",
        });
        return;
      }
      this.isShowAddDialog = true;
    },
    doAddAdv() {
      AdminMiscApi.addAdv(
        this.addDialogData.url,
        this.addDialogData.image
      ).then((resp) => {
        if (resp.data.code === "0") {
          //success
          this.$notify.success("提交成功");
          this.addDialogData = {};
          this.isShowAddDialog = false;
          location.reload();
        } else {
          this.$notify.error({
            title: "提交失败",
            message: resp.data.message,
          });
        }
      });
    },
    onBeforeImageUpload(rawFile) {
      //判断大小
      if (rawFile.size / 1024 / 1024 > 2) {
        this.$notify.error({ title: "文件大小不能超过 2MB!" });
        return false;
      }
      //判断类型
      let fileType = String(rawFile.type);
      if (!fileType.includes("image/jpeg") && !fileType.includes("image/png")) {
        console.log(fileType);
        this.$notify.error({ title: "文件必须是JPG或者PNG格式" });
        return false;
      }
      return true;
    },
    onImageUploadSuccess(response) {
      if (response.code === "0") {
        this.$notify.success({ title: "上传成功" });
        this.addDialogData.image = response.data;
      } else {
        this.$notify.error({
          title: "上传失败",
          message: response.data.message,
        });
      }
    },
    doRemoveAdv(advertisementId) {
      AdminMiscApi.removeAdv(advertisementId).then((resp) => {
        if (resp.data.code === "0") {
          //success
          this.$notify.success("操作成功");
          location.reload(); //refresh
        } else {
          this.$notify.error({
            title: "操作失败",
            message: resp.data.message,
          });
        }
      });
    },
  }, //methods
  mounted() {
    HomeApi.getAds().then((resp) => {
      this.adsList = resp.data.data;
    });
    this.uploadHeaders.AdminAuthorization = localStorage.getItem("adminToken");
  }, //mounted
};
</script>

<style scoped></style>
