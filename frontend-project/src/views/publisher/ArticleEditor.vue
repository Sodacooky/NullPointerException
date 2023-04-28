<template>
  <el-container>
    <el-main>
      <el-card>
        <!--文章信息区          -->
        <template #header>
          <span style="font-weight: bold; font-size: large">文章信息</span>
        </template>
        <!--          标题-->
        <el-input
          style="font-size: large"
          placeholder="文章不能没有标题，就像人不能没有名字"
        >
          <template #prepend>文章标题</template>
        </el-input>
        <div class="title-hips" style="font-size: small; color: gray">
          <p>一个亮眼的标题能为你吸引到更多的读者~</p>
        </div>
        <!--          分类-->
        <el-input
          style="font-size: large; margin-top: 4px"
          placeholder="推荐输入后从推荐分类选取"
        >
          <template #prepend>文章分类</template>
        </el-input>
        <div class="category-tips" style="font-size: small; color: gray">
          <p>推荐使用常见分类，便于检索和排行~~</p>
        </div>
        <div>
          推荐分类：
          <span v-for="item in suggestCategories" :key="item">
            <el-button style="margin: 4px">
              {{ item }}
            </el-button>
          </span>
        </div>
      </el-card>

      <!--        文章正文编写区-->
      <el-card :body-style="{ padding: 0 }" style="margin-top: 16px">
        <template #header>
          <span style="font-weight: bold; font-size: large">正文</span>
        </template>
        <mavon-editor
          ref="md"
          v-model="editorRawText"
          :toolbars="mavonToolbars"
          placeholder="你总得写点什么"
        />
      </el-card>

      <!--        文章发布操作区-->
      <el-card style="margin-top: 16px">
        <template #header>
          <span style="font-weight: bold; font-size: large">发布</span>
        </template>
        <!--两个同意          -->
        <div class="agreement-checkbox">
          <div>
            <el-checkbox
              label="我已知悉发布的文章不能违反国家法律与社区规章制度"
              v-model="isAgreeLaw"
              size="large"
            />
          </div>
          <div>
            <el-checkbox
              label="我保证发布的文章不存在非法抄袭等违反著作权行为"
              v-model="isAgreeCopyright"
              size="large"
            />
          </div>
        </div>
        <!--        发布按钮-->
        <div>
          <el-button v-if="isAgreeLaw && isAgreeCopyright" @click="doPublish()">
            确认发布
          </el-button>
          <el-button v-else disabled>确认发布(需要勾选同意上述两项)</el-button>
        </div>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import { mavonToolbars } from "@/api/mavonSettings";

export default {
  name: "ArticleEditor",
  computed: {
    mavonToolbars() {
      return mavonToolbars;
    },
  },
  data() {
    return {
      editorRawText: "",
      suggestCategories: ["Java", "求职", "工作"],
      isAgreeLaw: false,
      isAgreeCopyright: false,
    };
  }, //data
  methods: {
    doPublish() {
      console.log(this.editorRawText);
    },
  }, //methods
};
</script>

<style scoped></style>
