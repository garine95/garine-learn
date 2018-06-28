package com.garine.learn.common.provider.dao.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 分页查询条件实体
 *
 * @author wugx
 */
@Data
public class PageCriteria implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;


}
