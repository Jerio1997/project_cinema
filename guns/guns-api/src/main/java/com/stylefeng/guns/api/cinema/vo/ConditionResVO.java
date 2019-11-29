package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public class ConditionResVO implements Serializable {

    private static final long serialVersionUID = 3717857064774908409L;
    private List<AreaListBean> areaList;
    private List<BrandListBean> brandList;
    private List<HalltypeListBean> halltypeList;

    public List<AreaListBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaListBean> areaList) {
        this.areaList = areaList;
    }

    public List<BrandListBean> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandListBean> brandList) {
        this.brandList = brandList;
    }

    public List<HalltypeListBean> getHalltypeList() {
        return halltypeList;
    }

    public void setHalltypeList(List<HalltypeListBean> halltypeList) {
        this.halltypeList = halltypeList;
    }

    public static class AreaListBean implements Serializable{
        private static final long serialVersionUID = -6368524502969208815L;
        /**
         * active : false
         * areaId : 1
         * areaName : 朝阳区
         */

        private boolean active;
        private int areaId;
        private String areaName;

        public boolean getActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }
    }

    public static class BrandListBean implements Serializable{
        private static final long serialVersionUID = -4817793158858579969L;
        /**
         * active : false
         * brandId : 1
         * brandName : 大地影院
         */

        private boolean active;
        private int brandId;
        private String brandName;

        public boolean getActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }
    }

    public static class HalltypeListBean implements Serializable {
        private static final long serialVersionUID = 2567197049412782279L;
        /**
         * active : false
         * halltypeId : 1
         * halltypeName : IMAX厅
         */

        private boolean active;
        private int halltypeId;
        private String halltypeName;

        public boolean getActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public int getHalltypeId() {
            return halltypeId;
        }

        public void setHalltypeId(int halltypeId) {
            this.halltypeId = halltypeId;
        }

        public String getHalltypeName() {
            return halltypeName;
        }

        public void setHalltypeName(String halltypeName) {
            this.halltypeName = halltypeName;
        }
    }
}
