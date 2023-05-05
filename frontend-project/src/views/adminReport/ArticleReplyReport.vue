<template>
  <h1>文章回复举报处理</h1>
  <div class="query-area">
    <el-row>
      <el-col :span="4">
        <!--顺序选择  -->
        <el-select v-model="order">
          <el-option label="最早" value="desc" />
          <el-option label="最新" value="asc" />
        </el-select>
      </el-col>
      <el-col :span="1"></el-col>
      <el-col :span="10">
        <div class="pager" style="display: flex; align-items: center">
          <el-button-group>
            <el-button :disabled="page <= 1" :icon="ArrowLeft" type="default">
              上一页
            </el-button>
            <el-button type="default">
              下一页
              <el-icon class="el-icon--right">
                <ArrowRight />
              </el-icon>
            </el-button>
          </el-button-group>
          <span style="margin-left: 8px; max-width: 160px">
            <el-input v-model="page" type="number">
              <template #prepend>当前页</template>
            </el-input>
          </span>
        </div>
      </el-col>
    </el-row>
  </div>

  <!--结果表格  -->
  <el-table :data="tableData">
    <el-table-column label="文章标题" min-width="128px" prop="articleTitle" />
    <el-table-column
      label="回复摘要"
      min-width="256px"
      prop="articleReplyShortText"
    />

    <el-table-column label="发布用户" min-width="128px">
      <template #default="scope">
        <span style="align-items: center; display: flex">
          <el-avatar
            :src="UserApi.getUserAvatarUrl(scope.row.replyPublisherAvatar)"
            size="small"
            style="margin-right: 4px"
          />
          {{ scope.row.replyPublisherNickname }}
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
      keyword: "",
      page: 1,
      tableData: [
        {
          id: 114514,
          articleTitle:
            "这是一个问题的标题，我可能会很长，那也就这么长，不会更长了",
          articleReplyShortText:
            "这是一段问题的正文的文章摘要，我需要足够长，才能测试出系统是否有问题...",
          replyPublisherNickname: "发布者用户名",
          replyPublisherAvatar: "default",
        },
      ],
    };
  },
};
</script>

<style scoped></style>