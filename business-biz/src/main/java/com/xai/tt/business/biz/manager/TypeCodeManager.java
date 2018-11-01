package com.xai.tt.business.biz.manager;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianan.common.api.exception.TspException;
import com.tianan.common.api.jpa.JpaCriteria;
import com.tianan.common.core.manager.BaseManager;
import com.xai.tt.business.biz.common.util.CodeTools;
import com.xai.tt.business.biz.repository.TypeCodeRepository;
import com.xai.tt.business.client.entity.TypeCode;

@Service
public class TypeCodeManager extends BaseManager<TypeCode, Integer> {
	
    @Autowired
    public void setTypeCodeRepository(TypeCodeRepository typeCodeRepository){
        this.repository = typeCodeRepository;
    }
    public TypeCodeRepository getTypeCodeRepository(){
        return (TypeCodeRepository)this.repository;
    }
    
    @Transactional
    public void delete(Integer id) {
        repository.delete(id);
    }
    
   	@Transactional
	public void save(TypeCode typeCode) {
   		tcodeCheck(typeCode);
   		tnameCheck(typeCode);
   		
		super.save(typeCode);
		CodeTools.refresh(typeCode.getTcode());
	}
   	
   	private void tcodeCheck(TypeCode typeCode) {
   		JpaCriteria criteria = JpaCriteria.instance("tcode", typeCode.getTcode());
   		if(typeCode.getId() != null) {
   			criteria.notEqual("id", typeCode.getId());
   		}
   		
   		if(super.count(criteria) > 0) {
   			throw new TspException("类型代码[" + typeCode.getTcode() + "]已存在！");
   		}
   	}
   	
   	private void tnameCheck(TypeCode typeCode) {
   		JpaCriteria criteria = JpaCriteria.instance("tname", typeCode.getTname());
   		if(typeCode.getId() != null) {
   			criteria.notEqual("id", typeCode.getId());
   		}
   		
   		if(super.count(criteria) > 0) {
   			throw new TspException("类型名称[" + typeCode.getTname() + "]已存在！");
   		}
   	}
}
