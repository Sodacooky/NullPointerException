<template>
  <div
    class="the-container"
    style="padding: 8px 8px 8px 0; display: flex; align-items: center"
  >
    <el-button-group>
      <el-button
        type="default"
        :icon="ArrowLeft"
        @click="$emit('prevBtnClick')"
        :disabled="currentPage <= 1"
      >
        上一页
      </el-button>
      <el-button
        type="default"
        @click="$emit('nextBtnClick')"
        :disabled="maxPage !== null && currentPage >= maxPage"
      >
        下一页
        <el-icon class="el-icon--right"><ArrowRight /> </el-icon>
      </el-button>
    </el-button-group>
    <span style="margin-left: 8px">
      当前页：{{ currentPage === undefined ? "ERROR" : currentPage }}
    </span>
  </div>
</template>

<script>
import { ArrowLeft, ArrowRight } from "@element-plus/icons-vue";
import { emitChangeFn } from "element-plus";

export default {
  name: "Pager",
  methods: { emitChangeFn },
  props: ["currentPage", "maxPage"],
  computed: {
    ArrowLeft() {
      return ArrowLeft;
    },
  },
  components: { ArrowRight },
};
</script>

<style scoped></style>
