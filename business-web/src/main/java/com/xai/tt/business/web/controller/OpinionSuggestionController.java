package com.xai.tt.business.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xai.tt.business.annotation.LogAspect;
//import com.xai.tt.dc.client.request.AnalysisQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.mybatis.PageParam;
import com.tianan.common.core.support.DateET;
import com.tianan.common.core.support.poi.ExcelColumn;
import com.tianan.common.core.support.poi.ExcelColumnDateFormatter;
import com.tianan.common.core.support.poi.PoiET;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.biz.common.util.Constants;
import com.xai.tt.business.biz.common.util.DateUtils;
import com.xai.tt.business.biz.common.util.JsonUtils;
import com.xai.tt.business.biz.manager.UserManager;
import com.xai.tt.dc.client.entity.OpinionSuggestion;
import com.xai.tt.dc.client.inter.IOpinionSuggestionService;
import com.xai.tt.dc.client.query.OpinionSuggestionQuery;

/**
 * Created by linkun
 */
@Controller
@RequestMapping("opinionSuggestion")
public class OpinionSuggestionController extends BaseController {

    @Resource
    private IOpinionSuggestionService iOpinionSuggestionServicee;
    @Autowired
    private UserManager userManager;

    @RequestMapping(value = { "list" })
    public String list() {
    	System.out.println("opinionSuggestion/list");
        return "opinionSuggestion/list";
    }

    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?> queryPage(OpinionSuggestionQuery opinionSuggestionQuery, PageParam pageParam) {
        try {
            opinionSuggestionQuery.setPageNo(pageParam.getPageNum());
            opinionSuggestionQuery.setPageSize(pageParam.getPageSize());
            Result<PageInfo<OpinionSuggestion>> result = iOpinionSuggestionServicee.queryPage(opinionSuggestionQuery);
            if (result == null || result.getCode() != 0) {
                Result.createFailResult("未查询到结果");
            }
            result.getData().getTotal();
            PageData pageData = new PageData(result.getData().getTotal(), result.getData().getList().stream().map(item->{
                if (item.getContent().length() > 20) {
                    item.setContent(item.getContent().substring(0,20) + "...");
                }
                return item;
            }).collect(Collectors.toList()));
            return Result.createSuccessResult(pageData);
        } catch (Exception e) {
            logger.error("意见建议查询失败,request query:{}", JsonUtils.toJson(opinionSuggestionQuery), e);
            return Result.createFailResult("查询发生错误");
        }

    }

    @RequestMapping("/get")
    @ResponseBody
    public Result<?> get(Integer id) {
        logger.info("查询车辆信息，id={}", id);
        Result<OpinionSuggestion> result = iOpinionSuggestionServicee.findOpinionSuggestionById(id);
        return result;
    }

    @LogAspect(type = LogAspect.LogType.Update_OpinionSuggestion, objectNames = OpinionSuggestion.class)
    @RequestMapping("/save")
    @ResponseBody
    public Result<?> save(OpinionSuggestion helpFaq) {
        try {
            Result result = iOpinionSuggestionServicee.saveOpinionSuggestion(helpFaq);
            return result;
        } catch (Exception e) {
            logger.error("保存意见建议 admin接口返回失败，req:{}", JsonUtils.toJson(helpFaq),e);
            return Result.createFailResult("admin接口失败");
        }
    }

    @LogAspect(type = LogAspect.LogType.Del_OpinionSuggestion, argNames ={"id"})
    @RequestMapping("/del")
    @ResponseBody
    public Result<?> del(Integer id) {
        try {
            Result result = iOpinionSuggestionServicee.delById(id);
            return result;
        } catch (Exception e) {
            logger.error("删除意见建议admin接口返回失败，req:{}",id,e);
            return Result.createFailResult("admin接口失败");
        }

    }
    @LogAspect(type = LogAspect.LogType.Export_OpinionSuggestion, objectNames = OpinionSuggestionQuery.class)
    @RequestMapping("/export4Template")
    @ResponseBody
    public void export4Template(OpinionSuggestionQuery opinionSuggestionQuery, PageParam pageParam,String rangeTime, HttpServletRequest request, HttpServletResponse response) {
        logger.info("export4Template criteria:{}", rangeTime);
        opinionSuggestionQuery.setPageNo(1);
        opinionSuggestionQuery.setPageSize(Constants.EXPOER_PAGE_SIZE);
        if (StringUtils.isNotBlank(rangeTime)) {
            String[] date = rangeTime.split(Constants.MIDDLE_LINE);
            opinionSuggestionQuery.setBeginCreateTime(DateUtils.format(date[0], DateUtils.YYYY_DD_MM_hh_mm_ss));
            opinionSuggestionQuery.setEndCreateTime(DateUtils.format(date[1], DateUtils.YYYY_DD_MM_hh_mm_ss));
        }

        Result<PageInfo<OpinionSuggestion>> result = iOpinionSuggestionServicee.queryPage(opinionSuggestionQuery);
        if (result.fail()) {
            try {
                response.getOutputStream().write(result.getMsg().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if (result.getData().getTotal() == 0) {
            try {
                response.getOutputStream().write("没有数据！".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        List<ExcelColumn> columns = new ArrayList<ExcelColumn>();
        // 列宽：以一个字符的1/256的宽度作为一个单位
        columns.add(new ExcelColumn("反馈时间", "createTime", (short) 6000, new ExcelColumnDateFormatter()));
        columns.add(new ExcelColumn("手机号", "userPhone", (short) 4000));
        columns.add(new ExcelColumn("反馈内容", "content", (short) 20000));
        columns.add(new ExcelColumn("图片url", "urls", (short) 25000));


        try {
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=opinionSuggestionList-" + DateET.format(new Date(System.currentTimeMillis()), "yyyyMMdd") + ".xlsx");
            // 以流的形式下载文件。
            response.setContentType("application/octet-stream");
            PoiET.export4bigData(columns, result.getData().getList(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
