import requests, { baseUrl } from "@/api/requests";

export class UserApi {
  static getCurrentUser() {
    return requests.get("/service-user/auth/getCurrentUser");
  }

  static getUserInfo(userId) {
    return requests.get("/service-user/public/getUserInfo", {
      params: { userId: userId },
    });
  }

  static getUserAvatarUrl(avatarFilename) {
    if (avatarFilename === undefined || avatarFilename === "default") {
      return "/default.jpg";
    } else {
      return baseUrl + "/service-user/public/avatar/" + avatarFilename;
    }
  }

  static getUserQuestion(userId, page) {
    return requests.get("/service-question/public/getQuestionInfoPublishedBy", {
      params: { userId: userId, page: page },
    });
  }

  static getUserAnswer(userId, page) {
    return requests.get("/service-question/public/getAnswerPublishedBy", {
      params: { userId: userId, page: page },
    });
  }

  static getUserArticle(userId, page) {
    return requests.get("/service-article/public/getArticlePublishedBy", {
      params: { userId: userId, page: page },
    });
  }
}
