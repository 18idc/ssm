package com.q18idc.ssm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author q18idc.com QQ993143799
 * Created by q18idc.com QQ993143799 on 2018/2/15
 */
public class ResultJson implements Serializable {
    private static final long serialVersionUID = 1079169172994441463L;

    private Integer total;
    private List rows;

    public ResultJson() {
    }

    public ResultJson(Integer total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ResultJson{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
