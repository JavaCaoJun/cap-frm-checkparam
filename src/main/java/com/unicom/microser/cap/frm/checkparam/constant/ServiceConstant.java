package com.unicom.microser.cap.frm.checkparam.constant;

/**
 * <div style="font-weight: 900">Description:</div>
 * <p>
 * <div style="font-weight: 900">date:2019/6/12</div>
 *
 * @author caojun
 */
public class ServiceConstant {


    /**
     * 数据处理正确，成功：'0000'
     */
    public static final String STATUS_SUCCESS = "0000";

    /**
     * 数据处理正确，成功：'0000'-返回信息
     */
    public static final String MSG_SUCCESS = "服务调用成功";

    /**
     * 请求参数错误，失败：'1000'
     */
    public static final String STATUS_EINVAL = "1000";

    /**
     * 请求参数错误，失败：'1000'-返回信息
     */
    public static final String MSG_EINVAL = "出错啦！请求参数错误，请检查！";


    /**
     * 服务降级错误，失败：'2000'
     */
    public static final String STATUS_FALLBACK = "2000";

    /**
     * 服务降级错误，失败：'2000'-返回信息
     */
    public static final String MSG_FALLBACK = "服务降级";


    /**
     * 系统错误（网络连接错误/超时），失败：'3000'
     */
    public static final String STATUS_SYSERROR = "3000";

    /**
     * 系统错误（网络连接错误/超时），失败：'3000'-返回信息
     */
    public static final String MSG_SYSERROR = "出错啦！网络连接错误";

    /**
     * 网络连接错误，失败：'3001'
     */
    public static final String STATUS_CONNECERROR = "3001";

    /**
     * 网络连接超时，失败：'3001'-返回信息
     */
    public static final String MSG_CONNECERROR = "网络连接出错啦！";

    /**
     * 网络连接超时，失败：'3002'
     */
    public static final String STATUS_CONNECTIMEOUT = "3002";

    /**
     * 网络连接超时，失败：'3002'-返回信息
     */
    public static final String MSG_CONNECTIMEOUT = "网络连接超时啦！";

    /**
     * 网络连接超时，失败：'3002'-返回信息
     */
    public static final String TXID_FAIL = "TxidError0000";





}
