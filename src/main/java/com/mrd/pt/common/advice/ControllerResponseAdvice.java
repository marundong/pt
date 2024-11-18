package com.mrd.pt.common.advice;

import com.google.common.collect.ImmutableMap;
import com.mrd.pt.common.code.ResultCode;
import com.mrd.pt.common.code.SysResultCode;
import com.mrd.pt.common.exception.BizException;
import com.mrd.pt.common.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author marundong
 */
@Slf4j
@RestControllerAdvice
public class ControllerResponseAdvice {
    //定义map，配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, SysResultCode.ERROR);
        EXCEPTIONS = builder.build();
    }

    @ExceptionHandler(BizException.class)
    public JsonResponse bizException(BizException bizException) {
        log.error("found bizException:{}", bizException.getMessage());
        ResultCode resultCode = bizException.getResultCode();
        JsonResponse jsonResponse = new JsonResponse(resultCode);
        if (bizException.getMessage() != null) {
            jsonResponse.setMsg(resultCode.msg() + ":" + bizException.getMessage());
        }
        return jsonResponse;
    }

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    public JsonResponse exception(Exception exception) {
        //记录日志
        log.error("found exception:{}", exception.getMessage(), exception);
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();//EXCEPTIONS构建成功
        }
        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        JsonResponse jsonResponse = resultCode != null ? new JsonResponse(resultCode) : new JsonResponse(SysResultCode.ERROR);
        if (exception.getMessage() != null) {
            jsonResponse.setMsg(jsonResponse.getMsg() + ":" + exception.getMessage());
        }

        return jsonResponse;
    }

}
