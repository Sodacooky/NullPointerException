<template>
    <el-container>
        <el-main>
            <el-card>
                <template #header>
                    <span style="font-size: large; font-weight: bold">个人设置</span>
                </template>
                <div style="display: flex; justify-content: center">
                    <el-form label-position="top">
                        <!--          头像展示与头像上传-->
                        <el-form-item label="头像">
                            <el-image
                                    fit="fill"
                                    src="https://s3.bmp.ovh/imgs/2022/12/14/18f7342584baa5a4.png"
                                    style="width: 128px"
                            />
                            <el-upload :limit="1" style="margin-left: 32px">
                                <el-button type="primary">点击上传新头像</el-button>
                                <template #tip>
                                    <div class="el-upload__tip">
                                        支持JPG与PNG格式，文件不大于5MB。
                                    </div>
                                </template>
                            </el-upload>
                        </el-form-item>
                        <el-divider/>
                        <el-form-item label="昵称">
                            <el-input style="width: 400px"></el-input>
                        </el-form-item>
                        <el-form-item label="自我介绍">
                            <el-input
                                    :autosize="{ minRows: 2, maxRows: 4 }"
                                    style="width: 400px"
                                    type="textarea"
                            />
                        </el-form-item>
                        <el-form-item>
                            <el-button>更新</el-button>
                        </el-form-item>
                        <el-divider/>
                        <el-form-item label="注册邮箱"> 114514@cc.jp</el-form-item>
                        <el-form-item label="用户编号ID"> 1145141818aac</el-form-item>
                    </el-form>
                </div>
            </el-card>
        </el-main>
    </el-container>
</template>

<script>
export default {
    name: "ProfileSettingsIndex",
    data() {
        return {
            imageUrl: false,
        };
    },
};
</script>

<style scoped></style>
