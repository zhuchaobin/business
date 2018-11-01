package com.xai.tt.business.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianan.common.api.annotation.JpaCriteriaEntity;
import com.tianan.common.api.bean.Code;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.jpa.JpaCriteria;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.annotation.LogAspect;
import com.xai.tt.business.annotation.LogAspect.LogType;
import com.xai.tt.business.biz.common.util.CodeTools;
import com.xai.tt.business.biz.manager.TypeCodeManager;
import com.xai.tt.business.client.entity.TypeCode;

@Controller
@RequestMapping("/typeCode")
public class TypeCodeController extends BaseController {
	@Autowired
	private TypeCodeManager typeCodeManager;

	@RequestMapping(value = { "list" })
	public String list() {
		return "typeCode/list";
	}

	@RequestMapping("/get")
	@ResponseBody
	public Result<?> get(Integer id) {
		TypeCode typeCode = typeCodeManager.get(id);
		return Result.createSuccessResult(typeCode);
	}

	@LogAspect(type=LogType.Update_TypeCode, objectNames=TypeCode.class)
	@RequestMapping("/save")
	@ResponseBody
	public Result<?> save(TypeCode tc) {
		String msg = validate(tc);
		
		if(StringUtils.isBlank(msg)) {
			return Result.createFailResult(msg);
		}
		
		typeCodeManager.save(tc);
		return Result.createSuccessResult();
	}
	
	@LogAspect(type=LogType.Delete_TypeCode, argNames="id")
	@RequestMapping("/del")
	@ResponseBody
	public Result<?> del(Integer id) {
		typeCodeManager.delete(id);
		return Result.createSuccessResult();
	}

	@RequestMapping("/query")
	@ResponseBody
	public Result<?> query(@JpaCriteriaEntity(TypeCode.class) JpaCriteria criteria) {
		PageData<TypeCode> typeCodeList = typeCodeManager.findPage(criteria);
		return Result.createSuccessResult(typeCodeList);
	}
	
	@RequestMapping("/listCode")
	@ResponseBody
	public Result<?> listCode(String type) {
		if(StringUtils.isBlank(type)) {
			return Result.createFailResult("参数无效！");
		}
		
		List<Code> codes = CodeTools.list(type);
		return Result.createSuccessResult(codes);
	}
	
	private String validate(TypeCode typeCode) {
		StringBuffer sb = new StringBuffer();
		
		if(StringUtils.isBlank(typeCode.getTcode())) {
			sb.append("类型代码不能为空！");
		}
		if(StringUtils.isBlank(typeCode.getTname())) {
			sb.append("类型名称不能为空！");
		}
		
		//，替换成,
		//去掉空格和\r
		//去掉空行
		String content = typeCode.getContent()
				.replaceAll("，", ",")
				.replaceAll("[ \r　]", "")
				.replaceAll("[\n]+", "\n");
		if(content.startsWith("\n")) {
			content = content.substring(1);
		}
			
		typeCode.setContent(content);

		if(StringUtils.isBlank(typeCode.getContent())) {
			sb.append("代码列表不能为空！");
		} else {
			String[] items = typeCode.getContent().split("\n");
			for (String item : items) {
				String[] codeItem = item.split(",");
				if(codeItem.length != 2 || codeItem.length != 3) {
					sb.append("代码列表格式不正确！");
				} 
			}
		}
		
		return sb.toString();
	}
}
