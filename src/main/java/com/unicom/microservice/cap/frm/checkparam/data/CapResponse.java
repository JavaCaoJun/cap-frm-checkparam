package com.unicom.microservice.cap.frm.checkparam.data;

import ch.qos.logback.classic.spi.LoggingEvent;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicom.microservice.cap.frm.checkparam.constant.ServiceConstant;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * <div style="font-weight: 900">Description:cap返回实体</div>
 * <p>
 * <div style="font-weight: 900">date:2019/6/12</div>
 *
 * @author caojun
 */
public class CapResponse {

    private static Logger logger =  LoggerFactory.getLogger(CapResponse.class);

    @ApiModelProperty(name = "STATUS", value = "服务请求结果编码", required = true, example = "0000")
    @JsonProperty("STATUS")
    @JSONField(name = "STATUS")
    private String status;
    @ApiModelProperty(name = "MSG", value = "服务请求结果描述", required = true, example = "服务调用成功！")
    @JSONField(name = "MSG")
    @JsonProperty("MSG")
    private String msg;
    @ApiModelProperty(name = "TXID", value = "服务流水号(自动生成)", required = true, example = "b393548b3dc5^1504165518997^70")
    @JSONField(name = "TXID")
    @JsonProperty("TXID")
    private String txid;
    @ApiModelProperty(name = "RSP", value = "服务返回业务数据", required = true)
    @JSONField(name = "RSP")
    @JsonProperty("RSP")
    private RespCaller rsp;

    /**
     * 默认构造函数
     */
    public CapResponse(){
        try{
            this.txid = this.generateTxid();
        }catch (Exception e){
            logger.error("can not get PtxId:", e);
        }
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public RespCaller getRsp() {
        return rsp;
    }

    public void setRsp(RespCaller rsp) {
        this.rsp = rsp;
    }
    /**
     * 请统一使用此方法获取txid
     * 获取Ptxid，截取后作为流水号txid，规则"容器ID-容器创建时间戳-被调用序号"
     * @return (String) txid
     */
    public static String generateTxid(){
        try{
            //实例化一个LoggingEvent对象，触发子线程拷贝MDC
            new LoggingEvent("mdcTrigger", (ch.qos.logback.classic.Logger)logger, null, null, null, null);
            String txid = MDC.get("PtxId");
            if (txid == null){
                logger.error("txid is null");
                return ServiceConstant.TXID_FAIL;
            }
            return txid;
        } catch(Exception e){
            logger.error("获取服务调用链txid异常",e);
            return ServiceConstant.TXID_FAIL;
        }
    }

    /**
     * 统一返回实体
     */
    public static CapResponse respCapResponse(String msg, String status, String respCode, String respMsg){
        CapResponse capResponse = new CapResponse();
        capResponse.setMsg(msg);
        capResponse.setStatus(status);
        RespCaller resp = new RespCaller();
        resp.setRspCode(respCode);
        resp.setRspDesc(respMsg);
        capResponse.setRsp(resp);
        return capResponse;
    }

    @Override
    public String toString() {
        return "CapResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", txid='" + txid + '\'' +
                ", rsp=" + rsp +
                '}';
    }
}
