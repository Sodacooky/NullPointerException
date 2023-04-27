<template>
  <h1>用户内容管理</h1>
  <!--搜索条件输入区    -->
  <div class="query-area">
    <el-row>
      <el-col :span="4">
        <el-select placeholder="请选择顺序" v-model="order">
          <el-option label="最新注册" value="time_desc" />
          <el-option label="最早注册" value="time_asc" />
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
      tableData: [
        {
          id: 114514,
          nickname: "用户昵称位置",
          description: "自我介绍描述",
          registerTime: "2022-11-11 00:00:00",
          avatar: "default",
        },
      ],
    };
  },
};
</script>

<style scoped></style>
