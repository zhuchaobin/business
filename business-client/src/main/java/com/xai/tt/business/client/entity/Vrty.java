package com.xai.tt.business.client.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.tianan.common.api.jpa.IncrEntity;

@Entity
public class Vrty extends IncrEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 俗名
     */
    @Column(name = "Comm_Nm")
    private String commNm;

    /**
     * 学名
     */
    @Column(name = "Ty_Nm")
    private String tyNm;

    /**
     * 是否启用
     */
    @Column(name = "Wthr_CmnUsed")
    private Integer wthrCmnused;

    /**
     * 品种介绍
     */
    @Column(name = "Vrty_Intd")
    private String vrtyIntd;

    /**
     * 图片url
     */
    @Column(name = "Pic_Url")
    private String picUrl;

    /**
     * 时间戳
     */
    @Column(name = "Tms")
    private Date tms;

    /**
     * 操作员
     */
    private String username;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否品种
     */
    private Boolean folder;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 父节点编号
     */
    private Integer pid;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String memo;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 价格上限
     */
    @Column(name = "Prc_UpLm")
    private Float prcUplm;

    /**
     * 价格下限
     */
    @Column(name = "Prc_LwrLmt")
    private Float prcLwrlmt;

    /**
     * 计量单位
     */
    @Column(name = "MsUnit")
    private String msunit;

    /**
     * 质量标准
     */
    @Column(name = "Qly_Std")
    private String qlyStd;

    /**
     * 规格
     */
    @Column(name = "Spec")
    private String spec;

    /**
     * 品牌
     */
    @Column(name = "Brnd")
    private String brnd;

    /**
     * 生产厂家
     */
    @Column(name = "Pd_Fctr")
    private String pdFctr;

    /**
     * 图文详情
     */
    @Column(name = "Pic_Detail")
    private byte[] picDetail;


    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取俗名
     *
     * @return Comm_Nm - 俗名
     */
    public String getCommNm() {
        return commNm;
    }

    /**
     * 设置俗名
     *
     * @param commNm 俗名
     */
    public void setCommNm(String commNm) {
        this.commNm = commNm;
    }

    /**
     * 获取学名
     *
     * @return Ty_Nm - 学名
     */
    public String getTyNm() {
        return tyNm;
    }

    /**
     * 设置学名
     *
     * @param tyNm 学名
     */
    public void setTyNm(String tyNm) {
        this.tyNm = tyNm;
    }

    /**
     * 获取是否启用
     *
     * @return Wthr_CmnUsed - 是否启用
     */
    public Integer getWthrCmnused() {
        return wthrCmnused;
    }

    /**
     * 设置是否启用
     *
     * @param wthrCmnused 是否启用
     */
    public void setWthrCmnused(Integer wthrCmnused) {
        this.wthrCmnused = wthrCmnused;
    }

    /**
     * 获取品种介绍
     *
     * @return Vrty_Intd - 品种介绍
     */
    public String getVrtyIntd() {
        return vrtyIntd;
    }

    /**
     * 设置品种介绍
     *
     * @param vrtyIntd 品种介绍
     */
    public void setVrtyIntd(String vrtyIntd) {
        this.vrtyIntd = vrtyIntd;
    }

    /**
     * 获取图片url
     *
     * @return Pic_Url - 图片url
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置图片url
     *
     * @param picUrl 图片url
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * 获取时间戳
     *
     * @return Tms - 时间戳
     */
    public Date getTms() {
        return tms;
    }

    /**
     * 设置时间戳
     *
     * @param tms 时间戳
     */
    public void setTms(Date tms) {
        this.tms = tms;
    }

    /**
     * 获取操作员
     *
     * @return username - 操作员
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置操作员
     *
     * @param username 操作员
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取是否品种
     *
     * @return folder - 是否品种
     */
    public Boolean getFolder() {
        return folder;
    }

    /**
     * 设置是否品种
     *
     * @param folder 是否品种
     */
    public void setFolder(Boolean folder) {
        this.folder = folder;
    }

    /**
     * 获取层级
     *
     * @return level - 层级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置层级
     *
     * @param level 层级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取父节点编号
     *
     * @return pid - 父节点编号
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父节点编号
     *
     * @param pid 父节点编号
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取顺序
     *
     * @return sort - 顺序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置顺序
     *
     * @param sort 顺序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取备注
     *
     * @return memo - 备注
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备注
     *
     * @param memo 备注
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取版本号
     *
     * @return version - 版本号
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取价格上限
     *
     * @return Prc_UpLm - 价格上限
     */
    public Float getPrcUplm() {
        return prcUplm;
    }

    /**
     * 设置价格上限
     *
     * @param prcUplm 价格上限
     */
    public void setPrcUplm(Float prcUplm) {
        this.prcUplm = prcUplm;
    }

    /**
     * 获取价格下限
     *
     * @return Prc_LwrLmt - 价格下限
     */
    public Float getPrcLwrlmt() {
        return prcLwrlmt;
    }

    /**
     * 设置价格下限
     *
     * @param prcLwrlmt 价格下限
     */
    public void setPrcLwrlmt(Float prcLwrlmt) {
        this.prcLwrlmt = prcLwrlmt;
    }

    /**
     * 获取计量单位
     *
     * @return MsUnit - 计量单位
     */
    public String getMsunit() {
        return msunit;
    }

    /**
     * 设置计量单位
     *
     * @param msunit 计量单位
     */
    public void setMsunit(String msunit) {
        this.msunit = msunit;
    }

    /**
     * 获取质量标准
     *
     * @return Qly_Std - 质量标准
     */
    public String getQlyStd() {
        return qlyStd;
    }

    /**
     * 设置质量标准
     *
     * @param qlyStd 质量标准
     */
    public void setQlyStd(String qlyStd) {
        this.qlyStd = qlyStd;
    }

    /**
     * 获取规格
     *
     * @return Spec - 规格
     */
    public String getSpec() {
        return spec;
    }

    /**
     * 设置规格
     *
     * @param spec 规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 获取品牌
     *
     * @return Brnd - 品牌
     */
    public String getBrnd() {
        return brnd;
    }

    /**
     * 设置品牌
     *
     * @param brnd 品牌
     */
    public void setBrnd(String brnd) {
        this.brnd = brnd;
    }

    /**
     * 获取生产厂家
     *
     * @return Pd_Fctr - 生产厂家
     */
    public String getPdFctr() {
        return pdFctr;
    }

    /**
     * 设置生产厂家
     *
     * @param pdFctr 生产厂家
     */
    public void setPdFctr(String pdFctr) {
        this.pdFctr = pdFctr;
    }

    /**
     * 获取图文详情
     *
     * @return Pic_Detail - 图文详情
     */
    public byte[] getPicDetail() {
        return picDetail;
    }

    /**
     * 设置图文详情
     *
     * @param picDetail 图文详情
     */
    public void setPicDetail(byte[] picDetail) {
        this.picDetail = picDetail;
    }
}
