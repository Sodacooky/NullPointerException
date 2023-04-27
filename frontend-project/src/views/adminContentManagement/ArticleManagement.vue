<template>
  <h1>文章内容管理</h1>
  <!--搜索条件输入区    -->
  <div class="query-area">
    <el-row>
      <el-col :span="4">
        <el-select placeholder="请选择顺序" v-model="order">
          <el-option label="最新发布" value="time_desc" />
          <el-option label="最早发布" value="time_asc" />
        </el-select>
      </el-col>
      <el-col :span="1" />
      <el-col :span="6">
        <el-input v-model="keyword">
          <template #prepend>关键词：</template>
          <template #append>
            <el-button type="primary">检索</el-button>
          </template>
        </el-input>
      </el-col>
      <el-col :span="1" />
      <el-col :span="10">
        <div class="page" style="display: flex; align-items: center">
          <el-button-group>
            <el-button type="default" :icon="ArrowLeft" :disabled="page <= 1">
              上一页
            </el-button>
            <el-button type="default">
              下一页
              <el-icon class="el-icon--right"><ArrowRight /> </el-icon>
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
    <el-table-column label="标题" min-width="128px" prop="title" />
    <el-table-column label="正文摘要" min-width="256px" prop="shortText" />

    <el-table-column label="发布用户" min-width="128px">
      <template #default="scope">
        <span style="align-items: center; display: flex">
          <el-avatar
            size="small"
            style="margin-right: 4px"
            :src="UserApi.getUserAvatarUrl(scope.row.publisherAvatar)"
          />
          {{ scope.row.publisherNickname }}
        </span>
      </template>
    </el-table-column>

    <el-table-column label="操作" min-width="96px">
      <template #default="scope">
        <el-button>详情</el-button>
        <el-button type="danger">移除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { UserApi } from "@/api/user";
import { ArrowLeft } from "@element-plus/icons-vue";

export default {
  name: "ArticleManagement",
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
      tableData: [
        {
          id: 114514,
          title: "这是一个文章的标题，我可能会很长，那也就这么长，不会更长了",
          shortText:
            "这是一段文章的正文的文章摘要，我需要足够长，才能测试出系统是否有问题...",
          publisherNickname: "发布者用户名",
          publisherAvatar: "default",
        },
      ],
    };
  },
};
</script>

<style scoped></style>
