<template>
    <el-collapse v-for="item in noticeData" :key="item.id">
        <el-collapse-item :name="item.id">
            <template #title>
                <div style="font-weight: bold; font-size: large">{{ item.title }}</div>
            </template>
            <div class="main-text">
                {{ item.text }}
            </div>
            <div class="notice-info" style="color: gray; text-align: right">
                {{ item.time }}
            </div>
        </el-collapse-item>
    </el-collapse>
</template>

<script>
export default {
    name: "SystemNoticeView",
    data() {
        return {
            noticeData: [],
        };
    },
    mounted() {
        this.noticeData.push({
            id: "1",
            title: "消息标题",
            text: "正文",
            time: "2022-2-22 22:22:22",
        });
        this.noticeData.push({
            id: "2",
            title: "消息标题2",
            text: "正文",
            time: "2022-2-22 22:22:23",
        });
    },
};
</script>

<style scoped></style>
