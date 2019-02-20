package com.xai.tt.business.web.controller;

import com.github.pagehelper.PageInfo;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.biz.common.util.JsonUtils;
/*import com.xai.tt.dc.client.entity.HelpFaq;*/
import com.xai.tt.dc.client.entity.KnowledgeCatalog;
import com.xai.tt.dc.client.inter.IKnowledgeBaseService;
import com.xai.tt.dc.client.query.KnowledgeCatalogQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 *
 */
@Controller
@RequestMapping("knowledgeCatalog")
public class KnowledgeCatalogController  extends BaseController {

    @Resource
    private IKnowledgeBaseService iKnowledgeBaseService;

    @RequestMapping(value = { "list" })
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("knowledgeCatalog/list");
        mav.addObject("catalogList", iKnowledgeBaseService.queryPage(new KnowledgeCatalogQuery()).getData().getList());
        return mav;
    }

    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?> queryPage(KnowledgeCatalogQuery knowledgeCatalogQuery, PageParam pageParam) {
        try {
            knowledgeCatalogQuery.setPageNo(pageParam.getPageNum());
            knowledgeCatalogQuery.setPageSize(pageParam.getPageSize());
            Result<PageInfo<KnowledgeCatalog>> result = iKnowledgeBaseService.queryPage(knowledgeCatalogQuery);
            if (result == null || result.getCode() != 0) {
                Result.createFailResult("未查询到结果");
            }
            result.getData().getTotal();
            PageData pageData = new PageData(result.getData().getTotal(), result.getData().getList());
            return Result.createSuccessResult(pageData);
        } catch (Exception e) {
            logger.error("知识库目录查询询失败,request query:{}", JsonUtils.toJson(knowledgeCatalogQuery), e);
            return Result.createFailResult("查询发生错误");
        }

    }

    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id) {
        logger.info("查询车辆信息，id={}", id);
        Result<KnowledgeCatalog> result = iKnowledgeBaseService.findKnowledgeCatalogById(id);
        return result;
    }
    @LogAspect(type= LogAspect.LogType.Update_KnowledgeCatalog,objectNames = KnowledgeCatalog.class)
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(KnowledgeCatalog knowledgeCatalog) {
        try {
            Result result = iKnowledgeBaseService.saveKnowledgeCatalog(knowledgeCatalog);
            return result;
        } catch (Exception e) {
            logger.error("保存知识库目录 admin接口返回失败，req:{}", JsonUtils.toJson(knowledgeCatalog),e);
            return Result.createFailResult("admin接口失败");
        }
    }

    @LogAspect(type = LogAspect.LogType.Del_KnowledgeCatalog, argNames = {"id"})
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id) {
        try {
            Result result = iKnowledgeBaseService.delCatalogById(id);
            return result;
        } catch (Exception e) {
            logger.error("删除FAQadmin接口返回失败，req:{}", id, e);
            return Result.createFailResult("admin接口失败");
        }

    }
}
