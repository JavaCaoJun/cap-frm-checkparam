package com.unicom.microser.cap.frm.checkparam.inter;

import com.alibaba.fastjson.JSONObject;
import com.unicom.microser.cap.frm.checkparam.constant.ParamCallerConstant;
import com.unicom.microser.cap.frm.checkparam.util.StreamUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <div style="font-weight: 900">Description:切面</div>
 * <p>
 * <div style="font-weight: 900">date:2019/6/12</div>
 *
 * @author caojun
 */
@Component
@Aspect
public class CheckParamInteceptor {

    public static Logger logger = null;

    @Before("com.unicom.microser.cap.frm.checkparam.inter.CheckParamInteceptor.pointCut()")
    public void checkParam(JoinPoint joinPoint) throws ClassNotFoundException, IOException {
        Signature signature = joinPoint.getSignature();
        if (logger == null) {
            logger = LoggerFactory.getLogger(signature.getDeclaringType());
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String method = request.getMethod();
        StringBuffer sb = new StringBuffer(ParamCallerConstant.LEFT_BRACKET);
        if ("GET".equals(method)) {
            Enumeration<String> strs = request.getParameterNames();
            while (strs.hasMoreElements()) {
                sb.append(ParamCallerConstant.CONLON);
                String name = strs.nextElement();
                sb.append(name + ParamCallerConstant.COLON_SEMICOLON_COLON);
                String value = request.getParameter(name);
                sb.append(value + ParamCallerConstant.CONLON_COMMA);
            }
            String paramStr = sb.toString();
            if(paramStr.length()==1){
                logger.info("CAP-REQ：" + ParamCallerConstant.LEFT_BRACKET + ParamCallerConstant.RIGHT_BRACKET);
            }else {
                String paramStrFinal = paramStr.substring(0, paramStr.length() - 1);
                logger.info("CAP-REQ：" + paramStrFinal + ParamCallerConstant.RIGHT_BRACKET);
            }
        } else {
            List<Object> logArgs = StreamUtil.streamOf(joinPoint.getArgs())
                    .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                    .collect(Collectors.toList());
            logger.info("CAP-REQ：" +JSONObject.toJSONString(logArgs));
        }
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)|| " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void pointCut() {
    }


}
