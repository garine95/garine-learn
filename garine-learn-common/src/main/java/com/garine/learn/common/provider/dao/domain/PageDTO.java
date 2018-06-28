/*
 * Copyright (c) 2016 4PX Information Technology Co.,Ltd. All rights reserved.
 */
package com.garine.learn.common.provider.dao.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 分页属性DTO
 *
 * @author wugx
 */
public class PageDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 分页数据
     */
    private List<T> data;

    public PageDTO() {
        super();
    }

    /**
     * @param total
     * @param data
     */
    public PageDTO(List<T> data, Long total) {
        super();
        this.data = data;
        this.total = total;
    }

    /**
     * @param total
     * @param pageNum
     * @param pageSize
     * @param data
     */
    public PageDTO(List<T> data, Long total,Integer pageNum,Integer pageSize,Integer pages) {
        super();
        this.data = data;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
    }

    /**
     * @return the pageNum
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * @param pageNum the pageNum to set
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * @return the pages
     */
    public Integer getPages() {
        return pages;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return String.format("PageDTO [pageNum=%s, pageSize=%s, total=%s, data=%s]", pageNum, pageSize, total, data);
    }
}
