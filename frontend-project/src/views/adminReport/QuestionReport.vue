<template>
  <h1>问题举报处理</h1>
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
    <el-table-column label="标题" min-width="128px" prop="goalQuestionTitle" />
    <el-table-column
      label="正文摘要"
      min-width="256px"
      prop="goalQuestionShortText"
    />

    <el-table-column label="发布用户" min-width="128px">
      <template #default="scope">
        <span style="align-items: center; display: flex">
          <el-avatar
            :src="UserApi.getUserAvatarUrl(scope.row.goalOwnerAvatar)"
            size="small"
            style="margin-right: 4px"
          />
          {{ scope.row.goalOwnerNickname }}
        </span>
      </template>
    </el-table-column>

    <el-table-column label="操作" min-width="96px">
      <template #default="scope">
        <el-button
          @click="openDetailDialog(scope.row.id, scope.row.goalQuestionId)"
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
    <template #header><span style="font-weight: bold">问题详情</span></template>
    <el-form label-position="right" label-width="96">
      <el-form-item label="问题ID">
        {{ detailDialogData.id }}
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="detailDialogData.title" />
      </el-form-item>
      <el-form-item label="分类">
        <el-input v-model="detailDialogData.category" />
      </el-form-item>
      <el-form-item label="正文">
        <el-input
          v-model="detailDialogData.text"
          :autosize="{ minRows: 4, maxRows: 8 }"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="发布者ID">
        {{ detailDialogData.publisherId }}
      </el-form-item>
      <el-form-item label="发布时间">
        {{ detailDialogData.publishTime }}
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
      <el-button type="primary" @click="doQuestionInfoUpdate()">更改</el-button>
      <el-button type="danger" @click="doRemove()">删除</el-button>
      <el-button
        v-if="detailDialogData.isProcessed === '0'"
        @click="doSetProcessed()"
        >标记为已处理（忽略举报）</el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import { UserApi } from "@/api/user";
import { ArrowLeft } from "@element-plus/icons-vue";
import { AdminReportApi } from "@/api/adminReport";
import { ReadingApi } from "@/api/reading";
import { AdminContentManageApi } from "@/api/adminContentManage";

export default {
  name: "QuestionReport",
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
      AdminReportApi.getQuestionReport(
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
    openDetailDialog(reportId, questionId) {
      //打开窗口
      this.isShowDetailDialog = true;
      //填充问题信息
      ReadingApi.getQuestionInfo(questionId).then((resp) => {
        this.detailDialogData = resp.data.data;
        ReadingApi.getQuestionText(questionId).then((resp2) => {
          this.detailDialogData.text = resp2.data.data;
        });
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
    doQuestionInfoUpdate() {
      AdminContentManageApi.modifyQuestionInfo(
        this.detailDialogData.id,
        this.detailDialogData.title,
        this.detailDialogData.category,
        this.detailDialogData.text
      ).then((resp) => {
        if (resp.data.code === "0") {
          this.$notify.success("操作成功");
          this.isShowDetailDialog = false;
        } else {
          this.$notify.error({ title: "操作失败", message: resp.data.message });
        }
      });
    },
    doRemove() {
      this.$confirm(
        "删除问题将会把其下的回答也一并删除，确定吗？",
        "确定删除",
        {
          confirmButtonText: "是的，连同答案一起删除",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          //确认删除
          AdminContentManageApi.removeQuestion(this.detailDialogData.id).then(
            (resp) => {
              if (resp.data.code === "0") {
                this.$notify.success("操作成功");
              } else {
                this.$notify.error({
                  title: "操作失败",
                  message: resp.data.message,
                });
              }
            }
          );
        })
        .catch(() => {
          //取消
          this.$notify.info("操作取消");
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
