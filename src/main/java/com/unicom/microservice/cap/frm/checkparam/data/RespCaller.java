package com.unicom.microservice.cap.frm.checkparam.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * <div style="font-weight: 900">Description:</div>
 * <p>
 * <div style="font-weight: 900">date:2019/6/14</div>
 *
 * @author caojun
 */
public class RespCaller {
    @ApiModelProperty(name = "RSP_CODE", value = "业务返回编码", required = true, example = "0000")
    @JsonProperty("RSP_CODE")
    @JSONField(name = "RSP_CODE")
    private String rspCode;

    @ApiModelProperty(name = "RSP_DESC", value = "业务返回描述", required = true, example = "success")
    @JsonProperty("RSP_DESC")
    @JSONField(name = "RSP_DESC")
    private String rspDesc;


    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }

    @Override
    public String toString() {
        return "RespCaller{" +
                "rspCode='" + rspCode + '\'' +
                ", rspDesc='" + rspDesc + '\'' +
                '}';
    }
}
