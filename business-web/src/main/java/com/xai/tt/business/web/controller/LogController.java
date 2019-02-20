package com.xai.tt.business.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.core.support.DateET;
import com.tianan.common.core.support.poi.ExcelColumn;
import com.tianan.common.core.support.poi.ExcelColumnFormatter;
import com.tianan.common.core.support.poi.PoiET;
import com.tianan.common.mvc.bean.HttpCriteria;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.annotation.LogAspect.LogType;
import com.xai.tt.business.biz.manager.OperLogManager;
import com.xai.tt.business.client.entity.OperLog;

@Controller
@RequestMapping("log")
public class LogController extends BaseController {

    @Autowired
    private OperLogManager operLogManager;
    
    @RequestMapping(value = { "list" })
    public String list() {
        return "operlog/list";
    }

    @RequestMapping(value = { "queryPage" })
    @ResponseBody
    public Result<?> queryPage(HttpCriteria params) {
    	logger.info("日志查询请求参数:{}", params);
    	PageData<OperLog> logList = operLogManager.findPage(params.toJpaCriteria(OperLog.class));
        return Result.createSuccessResult(logList);
    }
    
    @RequestMapping("/get")
	@ResponseBody
	public Result<?> get(Integer id) {
    	logger.info("查询日志信息，id={}", id);
    	OperLog log = operLogManager.get(id);
		logger.info("查询日志信息返回结果：{}", JSON.toJSONString(log));
		return Result.createSuccessResult(log);
	}
    
    @LogAspect(type=LogType.Export_Log)
    @RequestMapping("/export4Template")
    @ResponseBody
    public void export4Template(HttpCriteria params, HttpServletRequest request, HttpServletResponse response) {
		logger.info("export4Template criteria:{}", params);
		
		List<OperLog> logList = operLogManager.findAll(params.toJpaCriteria(OperLog.class));
    	
		List<ExcelColumn> columns = new ArrayList<>();
        //列宽：以一个字符的1/256的宽度作为一个单位
        if (logList == null) {
            return;
        }
        columns.add(new ExcelColumn("用户名", "username", (short)10000));
        columns.add(new ExcelColumn("企业名称", "companyName", (short)10000));
        columns.add(new ExcelColumn("操作内容", "content", (short)10000));
        columns.add(new ExcelColumn("操作IP", "ip", (short)10000));
        columns.add(new ExcelColumn("操作时间", "operTime", (short)10000,new ExcelColumnFormatter<Date, String>() {
			@Override
			public String format(Date value) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return df.format(value);
			}

		}));
      
    	try {
    		// 设置response的Header
    		response.addHeader("Content-Disposition", "attachment;filename=operLog-" + DateET.format(new Date(System.currentTimeMillis()), "yyyyMMdd") + ".xlsx");
    		// 以流的形式下载文件。
    		response.setContentType("application/octet-stream");

			PoiET.export4bigData(columns, logList, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
