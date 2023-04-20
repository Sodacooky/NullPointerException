# 端口记录

| 端口   | 说明              |
|:-----|:----------------|
| 8080 | 网关，外部访问的入口      |
| 8081 | Question模块      |
| 8082 | Article模块       |
| 8083 | User模块          | 
| 8084 | Miscellaneous模块 |
| 8085 | Authc模块         |

# 消息类型

| type            | 描述          |
|:----------------|:------------|
| question_answer | 问题收到回答      |
| answer_reply    | 回答收到的回复     |
| article_reply   | 文章收到的回复     |
| system          | 来自系统、管理员的通知 |