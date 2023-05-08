<template>
  <h1>用户举报处理</h1>
  <div class="query-area">
    <el-row>
      <el-col :span="4">
        <!--顺序选择  -->
        <el-select v-model="order" @change="doNewFetch()">
          <el-option label="最早" value="desc" />
          <el-option label="最新" value="asc" />
        </el-select>
      </el-col>
      <el-col :span="1" />
      <el-col :span="9">
        <div class="pager" style="display: flex; align-items: center">
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
            <el-input v-model="page" type="number" @change="fetch()">
              <template #prepend>当前页</template>
            </el-input>
          </span>
        </div>
      </el-col>
      <el-col :span="4">
        <el-checkbox v-model="isShowProcessed" @change="doNewFetch()">
          展示已处理的</el-checkbox
        >
      </el-col>
    </el-row>
  </div>

  <!--结果表格  -->
  <el-table :data="tableData">
    <el-table-column label="头像" min-width="32px">
      <template #default="scope">
        <el-avatar :src="UserApi.getUserAvatarUrl(scope.row.goalUserAvatar)" />
      </template>
    </el-table-column>
    <el-table-column label="昵称" min-width="96px" prop="goalUserNickname" />
    <el-table-column
      label="自我介绍"
      min-width="160px"
      prop="goalUserDescription"
    />

    <el-table-column label="操作" min-width="96px">
      <template #default="scope">
        <el-button
          @click="openDetailDialog(scope.row.id, scope.row.goalUserId)"
        >
          详情与处理
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--展示详情编辑框-->
  <el-dialog
    v-model="isShowDetailDialog"
    class="question-detail-dialog"
    width="800px"
  >
    <template #header><span style="font-weight: bold">用户详情</span></template>
    <el-form label-position="right" label-width="96">
      <el-form-item label="用户ID">
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
      <el-form-item label="举报者ID">
        {{ detailDialogData.reporterId }}
      </el-form-item>
      <el-form-item label="举报附加消息">
        {{ detailDialogData.comment }}
      </el-form-item>
      <el-form-item label="举报时间">
        {{ detailDialogData.time }}
      </el-form-item>
      <el-form-item label="举报ID">
        {{ detailDialogData.reportId }}
      </el-form-item>
      <el-form-item label="处理情况">
        <span v-if="detailDialogData.isProcessed === '0'">
          <el-tag size="large" type="warning">未处理</el-tag>
        </span>
        <span v-else>
          <el-tag size="large" type="success">已处理</el-tag>
        </span>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="doInfoUpdate()">更改</el-button>
      <el-button
        v-if="detailDialogData.isBanned === '0'"
        type="danger"
        @click="doBan(detailDialogData.id)"
      >
        封禁
      </el-button>
      <el-button v-else type="warning" @click="doUnban(detailDialogData.id)">
        解封
      </el-button>
      <el-button
        v-if="detailDialogData.isProcessed === '0'"
        @click="doSetProcessed()"
      >
        标记为已处理（忽略举报）
      </el-button>
    </template>
  </el-dialog>
</template>

<script>
import { UserApi } from "@/api/user";
import { ArrowLeft } from "@element-plus/icons-vue";
import { AdminReportApi } from "@/api/adminReport";
import { AdminContentManageApi } from "@/api/adminContentManage";

export default {
  name: "ArticleReport",
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
      order: "desc",
      page: 1,
      isShowProcessed: false,
      tableData: [],
      isShowDetailDialog: false,
      detailDialogData: {},
    };
  }, //data
  methods: {
    fetch() {
      AdminReportApi.getUserReport(
        this.page,
        this.order.includes("asc"),
        this.isShowProcessed
      ).then((resp) => {
        if (resp.data.data.length < 1) {
          this.$notify.warning("没有更多数据");
        } else {
          this.tableData = resp.data.data;
        }
      });
    },
    doNewFetch() {
      this.page = 1;
      this.tableData = [];
      this.fetch();
    },
    doGoNextPage() {
      this.page++;
      this.fetch();
    },
    doGoPrevPage() {
      this.page--;
      this.fetch();
    },
    openDetailDialog(reportId, userId) {
      this.isShowDetailDialog = true;
      UserApi.getUserInfo(userId).then((resp) => {
        this.detailDialogData = resp.data.data;
        //填充举报信息
        AdminReportApi.getReport(reportId).then((resp) => {
          this.detailDialogData.reportId = resp.data.data.id; //=reportId
          this.detailDialogData.comment = resp.data.data.comment;
          this.detailDialogData.time = resp.data.data.time;
          this.detailDialogData.reporterId = resp.data.data.reporterId;
          this.detailDialogData.isProcessed = resp.data.data.isProcessed;
        });
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
    doBan() {
      AdminContentManageApi.banUser(this.detailDialogData.id).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success("操作成功");
        } else {
          this.$notify.error({ title: "操作失败", message: resp.data.message });
        }
      });
      this.doSetProcessed();
    },
    doUnban() {
      AdminContentManageApi.unbanUser(this.detailDialogData.id).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success("操作成功");
        } else {
          this.$notify.error({ title: "操作失败", message: resp.data.message });
        }
      });
    },
    doSetProcessed() {
      AdminReportApi.setProcessed(this.detailDialogData.reportId).then(
        (resp) => {
          if (resp.data.code === "0") {
            this.$notify.success("举报已处理");
            this.isShowDetailDialog = false;
          } else {
            this.$notify.warning(resp.data.message);
          }
        }
      );
    },
  }, //methods
  mounted() {
    this.fetch();
  }, //mounted
};
</script>

<style scoped></style>
