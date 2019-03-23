package com.xai.tt.business.biz.manager;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianan.common.api.exception.TspException;
import com.tianan.common.core.manager.BaseManager;
import com.xai.tt.business.biz.repository.VrtyRepository;
import com.xai.tt.business.client.entity.Vrty;

@Service
public class VrtyManager extends BaseManager<Vrty, Integer> {
/*	@Autowired
	private RoleVrtyManager roleVrtyManager;*/
	
    @Autowired
    public void setVrtyRepository(VrtyRepository vrtyRepository){
        this.repository = vrtyRepository;
    }
    
	@Transactional
	public void save(Vrty vrty) {
		logger.info("save vrty:{}", vrty);
		
    	if(vrty.getFolder())
    		vrty.setIcon("fa-cubes");
    	else 
    		vrty.setIcon("fa-cube");
		
		if(vrty.getId() == null) {
			if(vrty.getSort() == null) {
				getRepository().insert4sort(
						vrty.getName(),
						vrty.getCommNm(),
						vrty.getTyNm(),
						vrty.getWthrCmnused(),
						vrty.getVrtyIntd(),
						vrty.getPicUrl(),
						vrty.getPicDetail(),
						vrty.getTms(),
						vrty.getUsername(),
						vrty.getIcon(),
						vrty.getFolder(),
						vrty.getLevel(),
						vrty.getPid(),
					//	vrty.getSort(),
						vrty.getMemo(),
					//	vrty.getVersion(),
						vrty.getPrcUplm(),
						vrty.getPrcLwrlmt(),
						vrty.getMsunit(),
						vrty.getQlyStd(),
						vrty.getSpec(),
						vrty.getBrnd(),
						vrty.getPdFctr(),
						vrty.getModl());
			} else {
				super.save(vrty);
			}
		} else {
			Vrty dbVrty = repository.findOne(vrty.getId());
			if (dbVrty == null) {
				throw new TspException("菜单不存在或已删除！");
			}
			
			super.save(vrty);
		}
	}
	
    @Transactional
    @Override
    public void delete(Integer id) {
        logger.info("delete id:{}", id);
        
        if(getRepository().countByPid(id) > 0) {
			throw new TspException("存在子菜单，不能删除！");
        }
        
        super.delete(id);
        
 //       roleVrtyManager.deleteByVrtyId(id);
    }
    
/*    public List<Vrty> findByRoleIds(Integer... roleIds) {
    	return getRepository().findByRoleIds(roleIds);
    }*/

    private VrtyRepository getRepository() {
    	return (VrtyRepository)this.repository;
    }
}
