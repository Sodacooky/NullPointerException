<template>
  <h1>用户内容管理</h1>
  <!--搜索条件输入区    -->
  <div class="query-area">
    <el-row>
      <el-col :span="4">
        <el-select
          v-model="order"
          placeholder="请选择顺序"
          @change="doSearch()"
        >
          <el-option label="最新注册" value="time_desc" />
          <el-option label="最早注册" value="time_asc" />
        </el-select>
      </el-col>
      <el-col :span="1" />
      <el-col :span="6">
        <el-input v-model="keyword" @keydown.enter="doNewSearch()">
          <template #prepend>关键词：</template>
          <template #append>
            <el-button type="primary" @click="doNewSearch()">检索</el-button>
          </template>
        </el-input>
      </el-col>
      <el-col :span="1" />
      <el-col :span="10">
        <div class="page" style="display: flex; align-items: center">
          <el-button-group>
            <el-button
              :disabled="page <= 1"
              :icon="ArrowLeft"
              type="default"
              @click="doGoPrevPage()"
            >
              上一页
            </el-button>
            <el-button type="default" @click="doGoNextPage()">
              下一页
              <el-icon class="el-icon--right">
                <ArrowRight />
              </el-icon>
            </el-button>
          </el-button-group>
          <span style="margin-left: 8px; max-width: 160px">
            <el-input v-model="page" type="number" @change="doSearch()">
              <template #prepend>当前页</template>
            </el-input>
          </span>
        </div>
      </el-col>
    </el-row>
  </div>

  <!--结果表格  -->
  <el-table :data="tableData">
    <el-table-column label="头像" min-width="32px">
      <template #default="scope">
        <el-avatar :src="UserApi.getUserAvatarUrl(scope.row.avatar)" />
      </template>
    </el-table-column>
    <el-table-column label="昵称" min-width="96px" prop="nickname" />
    <el-table-column label="自我介绍" min-width="160px" prop="description" />
    <el-table-column label="注册时间" min-width="96px" prop="registerTime" />

    <el-table-column label="操作" min-width="96px">
      <template #default="scope">
        <el-button @click="openDetailDialog(scope.row.id)">详情</el-button>
        <el-button
          v-if="scope.row.isBanned === '0'"
          type="danger"
          @click="doBan(scope.row.id)"
        >
          封禁
        </el-button>
        <el-button v-else type="warning" @click="doUnban(scope.row.id)">
          解封
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--展示详情编辑框-->
  <el-dialog v-model="isShowDetailDialog" class="question-detail-dialog">
    <template #header><span style="font-weight: bold">用户详情</span></template>
    <el-form label-position="right" label-width="72">
      <el-form-item label="ID">
        {{ detailDialogData.id }}
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="detailDialogData.nickname" />
      </el-form-item>
      <el-form-item label="简介">
        <el-input
          v-model="detailDialogData.description"
          :autosize="{ minRows: 3, maxRows: 6 }"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="注册时间">
        {{ detailDialogData.registerTime }}
      </el-form-item>
      <el-form-item label="被封禁">
        <el-tag
          v-if="detailDialogData.isBanned === '0'"
          size="large"
          type="success"
        >
          否
        </el-tag>
        <el-tag v-else size="large" type="danger">是</el-tag>
      </el-form-item>
      <el-form-item label="头像文件">
        <el-input v-model="detailDialogData.avatar">
          <template #append>
            <el-button @click="detailDialogData.avatar = 'default'">
              重置
            </el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="当前头像">
        <el-image
          :src="UserApi.getUserAvatarUrl(detailDialogData.avatar)"
          style="width: 256px"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="doInfoUpdate()">更改</el-button>
    </template>
  </el-dialog>
</template>

<script>
import { UserApi } from "@/api/user";
import { ArrowLeft } from "@element-plus/icons-vue";
import { AdminContentManageApi } from "@/api/adminContentManage";
import { SearchApi } from "@/api/search";

export default {
  name: "UserManagement",
  computed: {
    UserApi() {
      return UserApi;
    },
    ArrowLeft() {
      return ArrowLeft;
    },
  },
  data() {
    return {
      order: "time_desc",
      keyword: "",
      page: 1,
      tableData: [],
      isShowDetailDialog: false,
      detailDialogData: {},
    };
  }, //data
  methods: {
    doNewSearch() {
      this.page = 1;
      this.doSearch();
    },
    doGoNextPage() {
      this.page++;
      this.doSearch();
    },
    doGoPrevPage() {
      this.page--;
      this.doSearch();
    },
    doSearch() {
      SearchApi.searchUserByRegisterTime(
        this.keyword,
        this.page,
        this.order.endsWith("asc")
      ).then((resp) => {
        this.tableData = resp.data.data;
        if (resp.data.data.length < 1) {
          this.$notify.warning({ title: "没有更多数据" });
        }
      });
    },
    openDetailDialog(userId) {
      this.isShowDetailDialog = true;
      UserApi.getUserInfo(userId).then((resp) => {
        this.detailDialogData = resp.data.data;
      });
    },
    doInfoUpdate() {
      AdminContentManageApi.modifyUser(
        this.detailDialogData.id,
        this.detailDialogData.nickname,
        this.detailDialogData.description,
        this.detailDialogData.avatar
      ).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success("操作成功");
          this.isShowDetailDialog = false;
        } else {
          this.$notify.error({ title: "操作失败", message: resp.data.message });
        }
      });
    },
    doBan(userId) {
      AdminContentManageApi.banUser(userId).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success("操作成功");
        } else {
          this.$notify.error({ title: "操作失败", message: resp.data.message });
        }
      });
    },
    doUnban(userId) {
      AdminContentManageApi.unbanUser(userId).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success("操作成功");
        } else {
          this.$notify.error({ title: "操作失败", message: resp.data.message });
        }
      });
    },
  }, //methods,
  mounted() {
    this.doNewSearch();
  }, //mounted
};
</script>

<style scoped></style>
