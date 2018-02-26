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

    public Condition() {
    }

    public Condition(Integer page, Integer rows) {
        this.page = page;
        this.rows = rows;
    }

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

}
