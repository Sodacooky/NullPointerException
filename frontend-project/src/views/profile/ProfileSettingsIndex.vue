<template>
  <el-container>
    <el-main>
      <el-card>
        <template #header>
          <span style="font-size: large; font-weight: bold">个人设置</span>
        </template>
        <div style="display: flex; justify-content: center">
          <el-form label-position="top">
            <!--          头像展示与头像上传-->
            <el-form-item label="头像">
              <el-image
                :show-file-list="false"
                :src="UserApi.getUserAvatarUrl(userInfo.avatar)"
                fit="fill"
                style="width: 128px"
              />
              <el-upload
                :action="UserApi.getUserAvatarUploadUrl()"
                :before-upload="handleBeforeUpload"
                :headers="headers"
                :limit="1"
                :on-success="handleOnSuccess"
                style="margin-left: 32px"
              >
                <el-button type="primary">点击上传新头像</el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    支持JPG与PNG格式，文件不大于2MB。
                  </div>
                </template>
              </el-upload>
            </el-form-item>
            <el-divider />

            <!--个人信息，昵称、自我描述修改              -->
            <el-form-item label="昵称">
              <el-input
                v-model="userInfo.nickname"
                style="width: 400px"
              ></el-input>
            </el-form-item>
            <el-form-item label="自我介绍">
              <el-input
                v-model="userInfo.description"
                :autosize="{ minRows: 2, maxRows: 4 }"
                style="width: 400px"
                type="textarea"
              />
            </el-form-item>
            <el-form-item>
              <el-button @click="doInfoUpdate()">更新个人信息</el-button>
            </el-form-item>
            <el-divider />

            <!--密码重置              -->
            <el-form-item label="原密码">
              <el-input
                v-model="oldPassword"
                style="width: 400px"
                type="password"
              ></el-input>
            </el-form-item>
            <el-form-item label="新密码">
              <el-input
                v-model="newPassword"
                style="width: 400px"
                type="password"
              ></el-input>
            </el-form-item>
            <el-form-item label="确认新密码">
              <el-input
                v-model="confirmNewPassword"
                style="width: 400px"
                type="password"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="doPasswordUpdate()">更新密码</el-button>
            </el-form-item>
            <el-divider />

            <!--用户ID和邮箱查看              -->
            <el-form-item label="注册邮箱"> 114514@cc.jp</el-form-item>
            <el-form-item label="用户编号ID"> 1145141818aac</el-form-item>
          </el-form>
        </div>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import { UserApi } from "@/api/user";

export default {
  name: "ProfileSettingsIndex",
  computed: {
    UserApi() {
      return UserApi;
    },
  },
  data() {
    return {
      //用户信息，也用于更新基本信息
      userInfo: {},
      //上传头像需要headers传递token
      headers: {},
      //密码更改相关
      oldPassword: "",
      newPassword: "",
      confirmNewPassword: "",
    };
  }, //data
  methods: {
    handleBeforeUpload(rawFile) {
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
    handleOnSuccess(response) {
      if (response.code === "0") {
        this.$notify.success({ title: "上传成功" });
        location.reload();
      } else {
        this.$notify.error({
          title: "上传失败",
          message: response.data.message,
        });
      }
    },
    doInfoUpdate() {
      UserApi.updateInfo(
        this.userInfo.nickname,
        this.userInfo.description
      ).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success({ title: "更新成功" });
          location.reload();
        } else {
          this.$notify.error({
            title: "更能失败",
            message: resp.data.message,
          });
        }
      });
    },
    doPasswordUpdate() {
      UserApi.updatePassword(
        this.oldPassword,
        this.newPassword,
        this.confirmNewPassword
      ).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success({ title: "更新成功，请重新登录" });
          localStorage.removeItem("token"); //后端已经移除了
          this.$router.push("/");
        } else {
          this.$notify.error({
            title: "更能失败",
            message: resp.data.message,
          });
        }
      });
    },
  },
  mounted() {
    //获取当前用户信息
    UserApi.getCurrentUser().then((resp) => {
      this.userInfo = resp.data.data;
    });
    //获取Token
    this.headers.Authorization = localStorage.getItem("token");
  },
};
</script>

<style scoped></style>
