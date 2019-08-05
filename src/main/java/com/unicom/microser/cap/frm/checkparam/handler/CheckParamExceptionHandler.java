package com.unicom.microser.cap.frm.checkparam.handler;

import com.alibaba.fastjson.JSONObject;
import com.unicom.microser.cap.frm.checkparam.constant.EmptyContant;
import com.unicom.microser.cap.frm.checkparam.constant.ParamCallerConstant;
import com.unicom.microser.cap.frm.checkparam.constant.ServiceConstant;
import com.unicom.microser.cap.frm.checkparam.data.CapResponse;
import com.unicom.microser.cap.frm.checkparam.data.RespCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;
;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <div style="font-weight: 900">Description:</div>
 * <p>
 * <div style="font-weight: 900">date:2019/6/12</div>
 *
 * @author caojun
 */

@ControllerAdvice
@ResponseBody
public class CheckParamExceptionHandler {
    private static Logger logger = null;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CapResponse MethodArgumentNotValidHandler(MethodArgumentNotValidException handler) {
        try {
            //String message = handler.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            BindingResult bindingResult = handler.getBindingResult();
            //获取对应的类
            MethodParameter parameter = handler.getParameter();
            Method method = parameter.getMethod();
            if (logger == null) {
                logger = LoggerFactory.getLogger(method.getDeclaringClass());
            }
            if (logger == null) {
                logger = LoggerFactory.getLogger(CheckParamExceptionHandler.class);
            }
            logger.info("CAP-REQ：" + JSONObject.toJSONString(bindingResult.getTarget()));
            StringBuffer sb = new StringBuffer();
            List<ObjectError> objectErrors = bindingResult.getAllErrors();
            List<String> emptyInfoList = EmptyContant.emptyInfoList;
            if (emptyInfoList == null || emptyInfoList.size() < 1) {
                emptyInfoList.add("不能为null");
                emptyInfoList.add("不能为空");
            }
            for (ObjectError objectError : objectErrors) {
                String defaulMessage = objectError.getDefaultMessage();
                Object[] objects = objectError.getArguments();
                DefaultMessageSourceResolvable dfd = (DefaultMessageSourceResolvable) objects[0];
                String emptyObject = dfd.getDefaultMessage();
                if (emptyInfoList.contains(defaulMessage)) {
                    sb.append(emptyObject);
                    sb.append(defaulMessage);
                } else {
                    sb.append(defaulMessage);
                }
            }
            CapResponse capResponse = new CapResponse();
            capResponse.setMsg(ServiceConstant.MSG_EINVAL);
            capResponse.setStatus(ServiceConstant.STATUS_SUCCESS);
            RespCaller respCaller = new RespCaller();
            respCaller.setRspCode(ParamCallerConstant.PARAM_ERROR);
            respCaller.setRspDesc(sb.toString());
            capResponse.setRsp(respCaller);
            logger.info("CAP-RESP：" + capResponse);
            return capResponse;
        } catch (Exception e) {
            return getCapResponse();
        }

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public CapResponse ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        try {
            String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
            Set<ConstraintViolation<?>> set = e.getConstraintViolations();
            for (ConstraintViolation<?> info : set) {
                if (logger == null) {
                    logger = LoggerFactory.getLogger(info.getRootBean().getClass());
                }
                if (logger == null) {
                    logger = LoggerFactory.getLogger(CheckParamExceptionHandler.class);
                }
                List<String> emptyInfoList = EmptyContant.emptyInfoList;
                String messe = info.getMessage();
                if (emptyInfoList == null || emptyInfoList.size() < 1) {
                    emptyInfoList.add("不能为null");
                    emptyInfoList.add("不能为空");
                }
                if (emptyInfoList.contains(messe)) {
                    throw new Exception();
                }
            }
            CapResponse capResponse = new CapResponse();
            capResponse.setMsg(ServiceConstant.MSG_EINVAL);
            capResponse.setStatus(ServiceConstant.STATUS_SUCCESS);
            RespCaller respCaller = new RespCaller();
            respCaller.setRspCode(ParamCallerConstant.PARAM_ERROR);
            respCaller.setRspDesc(message);
            capResponse.setRsp(respCaller);
            logger.info("CAP-RESP：" + capResponse);
            return capResponse;
        } catch (Exception ex) {
            return getCapResponse();
        }
    }

    @ExceptionHandler(BindException.class)
    public CapResponse BindExceptionHandler(BindException e) {
        try {
            //String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            StringBuffer sb = new StringBuffer();
            List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
            List<String> emptyInfoList = EmptyContant.emptyInfoList;
            for (ObjectError objectError : objectErrors) {
                String defaulMessage = objectError.getDefaultMessage();
                Object[] objects = objectError.getArguments();
                DefaultMessageSourceResolvable dfd = (DefaultMessageSourceResolvable) objects[0];
                String emptyObject = dfd.getDefaultMessage();
                if (emptyInfoList.contains(defaulMessage)) {
                    sb.append(emptyObject);
                    sb.append(defaulMessage);
                } else {
                    sb.append(defaulMessage);
                }
            }
            CapResponse capResponse = new CapResponse();
            capResponse.setMsg(ServiceConstant.MSG_EINVAL);
            capResponse.setStatus(ServiceConstant.STATUS_SUCCESS);
            RespCaller respCaller = new RespCaller();
            respCaller.setRspCode(ParamCallerConstant.PARAM_ERROR);
            respCaller.setRspDesc(sb.toString());
            capResponse.setRsp(respCaller);
            return capResponse;
        } catch (Exception ex) {
            return getCapResponse();
        }
    }

    private CapResponse getCapResponse() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(CheckParamExceptionHandler.class);
        }
        CapResponse capResponse = new CapResponse();
        capResponse.setMsg("请求参数非空或有误，请核查参数");
        capResponse.setStatus(ServiceConstant.STATUS_SUCCESS);
        RespCaller respCaller = new RespCaller();
        respCaller.setRspCode(ParamCallerConstant.PARAM_ERROR);
        respCaller.setRspDesc("请求参数非空或有误，请核查参数");
        capResponse.setRsp(respCaller);
        logger.info("CAP-RESP：" + capResponse);
        return capResponse;
    }

}
