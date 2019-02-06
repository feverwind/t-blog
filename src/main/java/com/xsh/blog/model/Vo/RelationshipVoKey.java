package com.xsh.blog.model.Vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class RelationshipVoKey implements Serializable {
    /**
     * 内容主键
     */
    private Integer cid;

    /**
     * 项目主键
     */
    private Integer mid;

    private static final long serialVersionUID = 1L;
}