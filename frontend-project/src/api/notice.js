import requests from "@/api/requests";

/**
 * 消息相关接口
 */
export class NoticeApi {
  static getQuestionAnswer() {
    return requests.get("/service-user/operation/notice/getQuestionAnswer");
  }

  static getArticleReply() {
    return requests.get("/service-user/operation/notice/getArticleReply");
  }
  static getSystem() {
    return requests.get("/service-user/operation/notice/getSystem");
  }

  static getAmount() {
    return requests.get("/service-user/operation/notice/getAmount");
  }

  static read(noticeId) {
    return requests.get("/service-user/operation/notice/read", {
      params: { noticeId },
    });
  }
}
