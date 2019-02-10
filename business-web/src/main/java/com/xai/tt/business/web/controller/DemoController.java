package com.xai.tt.business.web.controller;
import java.util.Date;

import com.tianan.common.api.annotation.JpaCriteriaEntity;
import com.tianan.common.api.bean.PageData;
import com.tianan.common.api.bean.Result;
import com.tianan.common.api.jpa.JpaCriteria;
import com.tianan.common.core.support.DateET;
import com.tianan.common.core.support.OssET;
import com.tianan.common.core.support.poi.ExcelColumn;
import com.tianan.common.core.support.poi.PoiET;
import com.tianan.common.mvc.controller.BaseController;
import com.xai.tt.business.client.entity.Demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

	@RequestMapping(value = { "demo1" })
	public String demo1() {
		return "demo/demo1";
	}
	@RequestMapping(value = { "demo2" })
	public String demo2() {
		return "demo/demo2";
	}
	@RequestMapping(value = { "demo4" })
	public String demo4() {
		return "demo/demo4";
	}
	@RequestMapping(value = { "demo5" })
	public String demo5() {
		return "demo/demo5";
	}
	@RequestMapping(value = { "demo6" })
	public ModelAndView demo6() {
		ModelAndView mav = new ModelAndView("demo/demo6");
		mav.addObject("ossPostPolicy", OssET.createPostPolicy("kl-tsp", "test", 3600));
		return mav;
	}


	@RequestMapping("/get")
	@ResponseBody
	public Result<?> get(Integer id) {

		Demo demo = new Demo();
		demo.setUsername("1");
		demo.setPassword("1");
		demo.setMobile("1");


		return Result.createSuccessResult(demo);
	}



	@RequestMapping("/query")
	@ResponseBody
	public Result<?> query(@JpaCriteriaEntity(Demo.class) JpaCriteria criteria) {
//		PageData<Demo> lsit = demoManager.findPage(criteria);

		List<Demo> list = new ArrayList<>();
		{
			Demo demo = new Demo();
			demo.setUsername("1");
			demo.setPassword("1");
			demo.setMobile("1");
			list.add(demo);

		}

		PageData<Demo> demoList=new PageData<>();

		demoList.setTotal(1);
		demoList.setRows(list);

		return Result.createSuccessResult(demoList);
	}

	@RequestMapping("/export4Template")
	@ResponseBody
	public void export4Template(@JpaCriteriaEntity(Demo.class) JpaCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
		logger.info("export4Template criteria:{}", criteria);
		List<Demo> demoList = new ArrayList<>();
		{
			Demo demo = new Demo();
			demo.setUsername("1");
			demo.setPassword("1");
			demo.setMobile("1");
			demoList.add(demo);

		}

		Map<String, Object> data = new HashMap<>();
		data.put("commnet", "建议1000条以下使用！");
		data.put("total", demoList.size());
		data.put("demoList", demoList);

		try {
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=demo-" + DateET.format(new Date(System.currentTimeMillis()), "yyyyMMdd") + ".xlsx");
			// 以流的形式下载文件。
			response.setContentType("application/octet-stream");

			InputStream templateIn = this.getClass().getResourceAsStream("/template/demoExport.xlsx");
			PoiET.export4template(templateIn, data, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getImportTemplate")
    @ResponseBody
    public void getImportTemplate(HttpServletResponse response) {
    	try(
    			InputStream in = this.getClass().getResourceAsStream("/template/demoImport.xlsx");
    			OutputStream out = response.getOutputStream();) {
    		
    		// 设置response的Header
    		response.addHeader("Content-Disposition", "attachment;filename=demoImport.xlsx");
    		// 以流的形式下载文件。
    		response.setContentType("application/octet-stream");
        	
        	byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }

            out.close();
            out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@RequestMapping("/importData")
	@ResponseBody
	public Result<?> importData(MultipartFile file, Boolean ajax) throws IOException {
		logger.info("importData");
		if(file == null) {
			return Result.createFailResult("请选择导入文件！");
		}
		
		List<ExcelColumn> columns = new ArrayList<>();
		columns.add(new ExcelColumn("username", 0));
		columns.add(new ExcelColumn("chineseName", 1));
		columns.add(new ExcelColumn("sex", 2));
		columns.add(new ExcelColumn("mobile", 3));
    	
    	List<Demo> list = PoiET.import2class(file.getInputStream(), columns, Demo.class);
//
//    	//TODO:校验
//    	demoManager.saveBatch(list);
    	
		return Result.createSuccessResult();
	}
	
}
