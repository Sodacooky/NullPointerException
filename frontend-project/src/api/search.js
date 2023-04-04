import requests from "@/api/requests";

export function searchQuestionByTime(keyword, page, isAsc) {
  return requests.get("/service-question/search/infoByTime", {
    params: { keyword, page, isAsc },
  });
}
