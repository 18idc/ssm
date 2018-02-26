package com.q18idc.ssm.entity;

import java.io.Serializable;

/**
 * @author q18idc.com QQ993143799
 * Created by q18idc.com QQ993143799 on 2018/2/15
 */
public class Condition implements Serializable {
    private static final long serialVersionUID = -2693013603271215065L;
    private Integer page;
    private Integer rows;
    private String key;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
