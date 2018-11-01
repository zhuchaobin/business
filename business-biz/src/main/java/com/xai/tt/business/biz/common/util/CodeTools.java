package com.xai.tt.business.biz.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tianan.common.api.bean.Code;
import com.tianan.common.api.jpa.JpaCriteria;
import com.xai.tt.business.biz.manager.TypeCodeManager;
import com.xai.tt.business.client.entity.TypeCode;

@Component
public class CodeTools {
	private static Map<String, List<Code>> cache = new ConcurrentHashMap<>();
	
	private static TypeCodeManager typeCodeManager;
	
	@Autowired
	public void setTypeCodeManager(TypeCodeManager typeCodeManager) {
		CodeTools.typeCodeManager = typeCodeManager;
	}

	public static Code get(String type, String code) {
		List<Code> codes = list(type);
		if(codes != null) {
			for (Code c : codes) {
				if(c.getCode().equals(code)) {
					return c;
				}
			}
		}
		
		return null;
	}
	
	public static List<Code> list(String type) {
		List<Code> codes = cache.get(type);
		if(codes != null) {
			return codes;
		}
		
		TypeCode typeCode = typeCodeManager.findOne(JpaCriteria.instance("tcode", type));
		if(typeCode != null) {
			codes = parse(typeCode.getContent());
			cache.put(type, codes);
		}
		return codes;
	}
	
	public static void refresh() {
		cache.clear();
	}
	
	public static void refresh(String type) {
		cache.remove(type);
	}
	
	private static List<Code> parse(String content) {
		List<Code> codes = new ArrayList<>();
		
		String[] items = content.split("\n");
		for (String item : items) {
			String[] codeItem = item.split(",");
			if(codeItem.length == 2) {
				codes.add(new Code(codeItem[0], codeItem[1], null));
			} else if(codeItem.length == 3) {
				codes.add(new Code(codeItem[0], codeItem[1], codeItem[2]));
			}
		}
		
		return codes;
	}
}
