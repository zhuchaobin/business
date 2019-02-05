package com.xai.tt.business.biz.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tianan.common.core.repository.BaseRepository;
import com.xai.tt.business.client.entity.Menu;
import com.xai.tt.business.client.entity.Vrty;

/**
 * Created by ssr on 2017/3/10.
 */

public interface VrtyRepository extends BaseRepository<Vrty, Integer> {
	
	@Query(value = "select * from menu where id in (select menu_id from role_menu where role_id in :roleIds) order by level, sort", nativeQuery = true)
	List<Menu> findByRoleIds(@Param("roleIds") Integer... roleIds);
	
	@Modifying
	@Query(value = "insert into vrty(name,Comm_Nm,Ty_Nm,Wthr_CmnUsed,Vrty_Intd,Pic_Url,Pic_Detail,Tms,username,icon,folder,level,pid,sort,memo,version,Prc_UpLm,Prc_LwrLmt,MsUnit,Qly_Std,Spec,Brnd,Pd_Fctr) "
			+ "values(:name,:commNm,:tyNm,:wthrCmnused,:vrtyIntd,:picUrl,:picDetail,:tms,:username,:icon,:folder,:level,:pid,(@@IDENTITY + 1) * 10,:memo,0,:prcUplm,:prcLwrlmt,:msunit,:qlyStd,:spec,:brnd,:pdFctr)", nativeQuery = true)
	int insert4sort(
			@Param("name") String name,
			@Param("commNm") String commNm,
			@Param("tyNm") String tyNm,
			@Param("wthrCmnused") Integer wthrCmnused,
			@Param("vrtyIntd") String vrtyIntd,
			@Param("picUrl") String picUrl,
			@Param("picDetail") byte[] picDetail,
			@Param("tms") Date tms,
			@Param("username") String username,
			@Param("icon") String icon,
			@Param("folder") boolean folder,
			@Param("level") Integer level,
			@Param("pid") Integer pid,
	//		@Param("sort") Integer sort,
			@Param("memo") String memo,
	//		@Param("version") Integer version,
			@Param("prcUplm") Float prcUplm,
			@Param("prcLwrlmt") Float prcLwrlmt,
			@Param("msunit") String msunit,
			@Param("qlyStd") String qlyStd,
			@Param("spec") String spec,
			@Param("brnd") String brnd,
			@Param("pdFctr") String pdFctr);	
	
	long countByPid(Integer pid);
}
