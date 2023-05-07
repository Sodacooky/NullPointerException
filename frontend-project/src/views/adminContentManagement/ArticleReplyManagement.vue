<template>
  <h1>文章回复内容管理</h1>
  <!--搜索条件输入区    -->
  <div class="query-area">
    <el-row>
      <el-col :span="4">
        <el-select
          v-model="order"
          placeholder="请选择顺序"
          @change="doSearch()"
        >
          <el-option label="最新发布" value="time_desc" />
          <el-option label="最早发布" value="time_asc" />
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
    <el-table-column
      label="文章标题"
      min-width="128px"
      prop="goalArticleTitle"
    />
    <el-table-column label="内容摘要" min-width="256px" prop="shortText" />

    <el-table-column label="发布用户" min-width="128px">
      <template #default="scope">
        <span style="align-items: center; display: flex">
          <el-avatar
            :src="UserApi.getUserAvatarUrl(scope.row.publisherAvatar)"
            size="small"
            style="margin-right: 4px"
          />
          {{ scope.row.publisherNickname }}
        </span>
      </template>
    </el-table-column>

    <el-table-column label="操作" min-width="96px">
      <template #default="scope">
        <el-button @click="openDetailDialog(scope.row.id)">详情</el-button>
        <el-button type="danger" @click="doRemove(scope.row.id)">
          移除
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--展示详情编辑框-->
  <el-dialog v-model="isShowDetailDialog" class="question-detail-dialog">
    <template #header>
      <span style="font-weight: bold">文章回复详情</span>
    </template>
    <el-form label-position="right" label-width="72">
      <el-form-item label="回复ID">
        {{ detailDialogData.id }}
      </el-form-item>
      <el-form-item label="文章ID">
        {{ detailDialogData.goalArticleId }}
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
    </el-form>
    <template #footer>
      <el-button type="primary" @click="doReplyUpdate()">更改</el-button>
    </template>
  </el-dialog>
</template>

<script>
import { UserApi } from "@/api/user";
import { ArrowLeft } from "@element-plus/icons-vue";
import { ReadingApi } from "@/api/reading";
import { AdminContentManageApi } from "@/api/adminContentManage";
import { SearchApi } from "@/api/search";

export default {
  name: "ArticleReplyManagement",
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
      SearchApi.searchArticleReplyByTime(
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
    openDetailDialog(replyId) {
      this.isShowDetailDialog = true;
      ReadingApi.getOneArticleReply(replyId).then((resp) => {
        this.detailDialogData = resp.data.data;
      });
    },
    doReplyUpdate() {
      AdminContentManageApi.modifyArticleReply(
        this.detailDialogData.id,
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
    doRemove(replyId) {
      this.$confirm("删除这条回复？", "确定删除", {
        confirmButtonText: "是的，删除",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //确认删除
          AdminContentManageApi.removeArticleReply(replyId).then((resp) => {
            if (resp.data.code === "0") {
              this.$notify.success("操作成功");
            } else {
              this.$notify.error({
                title: "操作失败",
                message: resp.data.message,
              });
            }
          });
        })
        .catch(() => {
          //取消
          this.$notify.info("操作取消");
        });
    },
  }, //methods,
  mounted() {
    this.doNewSearch();
  }, //mounted
};
</script>

<style scoped></style>
