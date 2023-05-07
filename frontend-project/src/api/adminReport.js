import requests from "@/api/requests";

export class AdminReportApi {
  static getQuestionReport(page, isAsc, isShowProcessed) {
    return requests.get("/service-miscellaneous/auth/admin/getQuestionReport", {
      params: {
        page,
        isAsc,
        isShowProcessed,
      },
    });
  }

  static getAnswerReport(page, isAsc, isShowProcessed) {
    return requests.get("/service-miscellaneous/auth/admin/getAnswerReport", {
      params: {
        page,
        isAsc,
        isShowProcessed,
      },
    });
  }

  static getArticleReport(page, isAsc, isShowProcessed) {
    return requests.get("/service-miscellaneous/auth/admin/getArticleReport", {
      params: {
        page,
        isAsc,
        isShowProcessed,
      },
    });
  }

  static getArticleReplyReport(page, isAsc, isShowProcessed) {
    return requests.get(
      "/service-miscellaneous/auth/admin/getArticleReplyReport",
      {
        params: {
          page,
          isAsc,
          isShowProcessed,
        },
      }
    );
  }

  static getUserReport(page, isAsc, isShowProcessed) {
    return requests.get("/service-miscellaneous/auth/admin/getUserReport", {
      params: {
        page,
        isAsc,
        isShowProcessed,
      },
    });
  }
}
