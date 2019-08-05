package com.unicom.microserv.cap.frm.checkparam.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * <div style="font-weight: 900">Description:</div>
 * <p>
 * <div style="font-weight: 900">date:2019/6/17</div>
 *
 * @author caojun
 */
public class EmptyContant {

    public static final List<String> emptyInfoList=new ArrayList<>();
    static {
        emptyInfoList.add("不能为null");
        emptyInfoList.add("不能为空");
    }
}
