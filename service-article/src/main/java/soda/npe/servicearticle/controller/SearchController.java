package soda.npe.servicearticle.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soda.npe.common.controller.Response;
import soda.npe.servicearticle.service.ArticleService;

/**
 * 处理问题相关的搜索，包括问题搜索和答案搜索
 */
@RestController
@RequestMapping("/public/search")
public class SearchController {

    @Resource
    private ArticleService articleService;

    /**
     * 按照时间顺序搜索文章，从文章的标题、标签、正文中匹配
     *
     * @param keyword 关键词
     * @param page    页码 from 1
     * @param isAsc   是否时间升序（从旧到新）
     * @return 当前页下，找到的文章实体列表，均去除了正文
     */
    @GetMapping("/byTime")
    public Response byTime(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return Response.ok(null, articleService.searchByTime(keyword, page, isAsc));
    }

    /**
     * 按照点赞用户数量顺序搜索文章，从文章的标题、标签、正文中匹配
     *
     * @param keyword 关键词
     * @param page    页码
     * @param isAsc   是否数量升序（从少到多）
     * @return 当前页下，找到的文章实体列表，均去除了正文
     */
    @GetMapping("/byApproval")
    public Response byApproval(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return Response.ok(null, articleService.searchByApproval(keyword, page, isAsc));
    }

    /**
     * 按照回复数量顺序搜索文章....
     *
     * @param keyword 关键词
     * @param page    页
     * @param isAsc   是否数量升序（从少到多）
     * @return 当前页下，找到的文章实体列表，均去除了正文
     */
    @GetMapping("/byReplyAmount")
    public Response byReplyAmount(String keyword, Integer page, Boolean isAsc) {
        if (page == null || page < 1) page = 1;
        if (isAsc == null) isAsc = false;
        return Response.ok(null, articleService.searchByReplyAmount(keyword, page, isAsc));
    }

}
