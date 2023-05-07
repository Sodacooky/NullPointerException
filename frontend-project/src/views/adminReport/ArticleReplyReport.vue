<template>
  <h1>文章回复举报处理</h1>
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
    <el-table-column
      label="文章标题"
      min-width="128px"
      prop="goalArticleTitle"
    />
    <el-table-column
      label="正文摘要"
      min-width="256px"
      prop="goalArticleReplyShortText"
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
        <el-button>详情与处理</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { UserApi } from "@/api/user";
import { ArrowLeft } from "@element-plus/icons-vue";
import { AdminReportApi } from "@/api/adminReport";

export default {
  name: "ArticleReplyReport",
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
    };
  }, //data
  methods: {
    fetch() {
      AdminReportApi.getArticleReplyReport(
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
  }, //methods
  mounted() {
    this.fetch();
  }, //mounted
};
</script>

<style scoped></style>
