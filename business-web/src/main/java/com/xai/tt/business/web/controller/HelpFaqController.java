package com.xai.tt.business.web.controller;

import com.github.pagehelper.PageInfo;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.biz.common.util.JsonUtils;
import com.xai.tt.dc.client.entity.HelpFaq;
import com.xai.tt.dc.client.inter.IHelpFaqService;
import com.xai.tt.dc.client.query.HelpFaqQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by linkun
 */
@Controller
@RequestMapping("helpFaq")
public class HelpFaqController extends BaseController {
    @Resource
    private IHelpFaqService iHelpFaqService;

    @RequestMapping(value = { "list" })
    public String list() {
        return "helpFaq/list";
    }

    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?> queryPage(HelpFaqQuery helpFaqQuery, PageParam pageParam) {
        try {
            helpFaqQuery.setPageNo(pageParam.getPageNum());
            helpFaqQuery.setPageSize(pageParam.getPageSize());
            Result<PageInfo<HelpFaq>> result = iHelpFaqService.queryPage(helpFaqQuery);
            if (result == null || result.getCode() != 0) {
                Result.createFailResult("未查询到结果");
            }
            result.getData().getTotal();
            PageData pageData = new PageData(result.getData().getTotal(), result.getData().getList());
            return Result.createSuccessResult(pageData);
        } catch (Exception e) {
            logger.error("FAQ查询失败,request query:{}", JsonUtils.toJson(helpFaqQuery), e);
            return Result.createFailResult("查询发生错误");
        }

    }

    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id) {
        logger.info("查询车辆信息，id={}", id);
        Result<HelpFaq> result = iHelpFaqService.findHelpFaqById(id);
        return result;
    }

    @LogAspect(type= LogAspect.LogType.Update_HelpFaq,objectNames = HelpFaq.class)
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(HelpFaq helpFaq) {
        try {
            Result result = iHelpFaqService.saveHelpFaq(helpFaq);
            return result;
        } catch (Exception e) {
            logger.error("保存FAQ admin接口返回失败，req:{}", JsonUtils.toJson(helpFaq),e);
            return Result.createFailResult("admin接口失败");
        }
    }

    @LogAspect(type = LogAspect.LogType.Del_HelpFaq, argNames = {"id"})
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id) {

        try {
            Result result = iHelpFaqService.delById(id);
            return result;
        } catch (Exception e) {
            logger.error("删除FAQadmin接口返回失败，req:{}", id, e);
            return Result.createFailResult("admin接口失败");
        }

    }
}
