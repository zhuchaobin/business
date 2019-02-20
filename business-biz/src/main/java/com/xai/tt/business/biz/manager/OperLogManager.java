package com.xai.tt.business.biz.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianan.common.core.manager.BaseManager;
import com.xai.tt.business.biz.repository.OperLogRepository;
import com.xai.tt.business.client.entity.OperLog;

@Service
public class OperLogManager extends BaseManager<OperLog, Integer> {
	
	@Autowired
    public void setOperLogRepository(OperLogRepository operLogRepository){
        this.repository = operLogRepository;
    }
    public OperLogRepository getOperLogRepository(){
        return (OperLogRepository)this.repository;
    }

	//@Transactional
	public void save(OperLog model) {
		super.save(model);
	}
   	
   	
}
